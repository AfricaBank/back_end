package org.africa.bank.service.impl;

import jakarta.persistence.criteria.Predicate;
import org.africa.bank.dto.TiersDTO;
import org.africa.bank.entity.Tiers;
import org.africa.bank.repository.TiersRepository;
import org.africa.bank.service.TiersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TiersServiceImpl implements TiersService {

    @Autowired
    private TiersRepository tiersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TiersDTO createTiers(TiersDTO tiersDTO) {
        // Validation des doublons
        if (tiersDTO.getEmail() != null && isEmailExists(tiersDTO.getEmail())) {
            throw new RuntimeException("L'email existe déjà");
        }

        if (tiersDTO.getMobile() != null && isMobileExists(tiersDTO.getMobile())) {
            throw new RuntimeException("Le numéro de mobile existe déjà");
        }

        Tiers tiers = convertToEntity(tiersDTO);
        tiers = tiersRepository.save(tiers);
        return convertToDTO(tiers);
    }

    @Override
    public TiersDTO updateTiers(Long id, TiersDTO tiersDTO) {
        Tiers existingTiers = tiersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tiers non trouvé avec l'id: " + id));

        // Mettre à jour les champs
        modelMapper.map(tiersDTO, existingTiers);

        // Sauvegarder
        Tiers updatedTiers = tiersRepository.save(existingTiers);
        return convertToDTO(updatedTiers);
    }

    @Override
    public void deleteTiers(Long id) {
        if (!tiersRepository.existsById(id)) {
            throw new RuntimeException("Tiers non trouvé avec l'id: " + id);
        }
        tiersRepository.deleteById(id);
    }

    @Override
    public TiersDTO getTiersById(Long id) {
        Tiers tiers = tiersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tiers non trouvé avec l'id: " + id));
        return convertToDTO(tiers);
    }

    @Override
    public Page<TiersDTO> getAllTiers(Pageable pageable) {
        return tiersRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<TiersDTO> searchTiers(Map<String, Object> searchCriteria) {
        return tiersRepository.findAll((root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    if (searchCriteria.containsKey("nom")) {
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nom")),
                                "%" + searchCriteria.get("nom").toString().toLowerCase() + "%"
                        ));
                    }

                    if (searchCriteria.containsKey("prenom")) {
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("prenom")),
                                "%" + searchCriteria.get("prenom").toString().toLowerCase() + "%"
                        ));
                    }

                    if (searchCriteria.containsKey("typeTiers")) {
                        predicates.add(criteriaBuilder.equal(root.get("typeTiers"), searchCriteria.get("typeTiers")));
                    }

                    if (searchCriteria.containsKey("dateNaissance")) {
                        predicates.add(criteriaBuilder.equal(root.get("dateNaissance"), searchCriteria.get("dateNaissance")));
                    }

                    if (searchCriteria.containsKey("numeroIdentifiantFiscal")) {
                        predicates.add(criteriaBuilder.equal(root.get("numeroIdentifiantFiscal"),
                                searchCriteria.get("numeroIdentifiantFiscal")));
                    }

                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TiersDTO> findByNom(String nom) {
        return tiersRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TiersDTO> findByPrenom(String prenom) {
        return tiersRepository.findByPrenomContainingIgnoreCase(prenom)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TiersDTO> findByNomAndPrenom(String nom, String prenom) {
        return tiersRepository.rechercherParNomPrenom(nom, prenom)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TiersDTO findByEmail(String email) {
        return tiersRepository.findByEmail(email)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public TiersDTO findByMobile(String mobile) {
        return tiersRepository.findByMobile(mobile)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<TiersDTO> findByTypeTiers(String typeTiers) {
        return tiersRepository.findByTypeTiers(typeTiers)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isTiersExists(Long id) {
        return tiersRepository.existsById(id);
    }

    @Override
    public boolean isEmailExists(String email) {
        return tiersRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isMobileExists(String mobile) {
        return tiersRepository.findByMobile(mobile).isPresent();
    }

    @Override
    public boolean isNumeroIdentifiantFiscalExists(String numero) {
        return !tiersRepository.findByNumeroIdentifiantFiscal(numero).isEmpty();
    }

    @Override
    public TiersDTO convertToDTO(Tiers tiers) {
        return modelMapper.map(tiers, TiersDTO.class);
    }

    @Override
    public Tiers convertToEntity(TiersDTO tiersDTO) {
        return modelMapper.map(tiersDTO, Tiers.class);
    }

    @Override
    public Tiers getEntityById(Long id) {
        return tiersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tiers non trouvé avec l'id: " + id));
    }

    @Override
    public List<Tiers> searchEntities(Map<String, Object> criteria) {
        return searchTiers(criteria).stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}