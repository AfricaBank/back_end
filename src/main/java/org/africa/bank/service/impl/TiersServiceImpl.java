package org.africa.bank.service.impl;

import org.africa.bank.dto.CompteBancaireDTO;
import org.africa.bank.dto.PersonneEnChargeDTO;
import org.africa.bank.dto.TiersDTO;
import org.africa.bank.entity.CompteBancaire;
import org.africa.bank.entity.PersonneEnCharge;
import org.africa.bank.entity.Tiers;
import org.africa.bank.exception.DuplicateResourceException;
import org.africa.bank.exception.ResourceNotFoundException;
import org.africa.bank.repository.TiersRepository;
import org.africa.bank.service.TiersService;
import org.africa.bank.specification.TiersSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TiersServiceImpl implements TiersService {

    private final TiersRepository tiersRepository;
    private final ModelMapper modelMapper;

    public TiersServiceImpl(TiersRepository tiersRepository,
                            ModelMapper modelMapper) {
        this.tiersRepository = tiersRepository;
        this.modelMapper     = modelMapper;
    }

    @Override
    public Tiers saveOrUpdate(Tiers tiers) {
        return tiersRepository.save(tiers);
    }

    @Override
    public TiersDTO createTiers(TiersDTO tiersDTO) {
        if (tiersDTO.getEmail() != null && isEmailExists(tiersDTO.getEmail())) {
            throw new DuplicateResourceException(
                    "L'email existe déjà : " + tiersDTO.getEmail());
        }
        if (tiersDTO.getMobile() != null && isMobileExists(tiersDTO.getMobile())) {
            throw new DuplicateResourceException(
                    "Le mobile existe déjà : " + tiersDTO.getMobile());
        }
        return convertToDTO(tiersRepository.save(convertToEntity(tiersDTO)));
    }

    @Override
    public TiersDTO updateTiers(Long id, TiersDTO tiersDTO) {
        Tiers existing = tiersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tiers", id));
        modelMapper.map(tiersDTO, existing);
        // Mettre à jour comptes et personnes en charge si fournis
        if (tiersDTO.getComptes() != null) {
            existing.getAccounts().clear();
            tiersDTO.getComptes().forEach(dto -> {
                CompteBancaire c = modelMapper.map(dto, CompteBancaire.class);
                c.setTiers(existing);
                existing.getAccounts().add(c);
            });
        }
        if (tiersDTO.getPersonnesEnCharge() != null) {
            existing.getPersonnes().clear();
            tiersDTO.getPersonnesEnCharge().forEach(dto -> {
                PersonneEnCharge p = modelMapper.map(dto, PersonneEnCharge.class);
                p.setTiers(existing);
                existing.getPersonnes().add(p);
            });
        }
        return convertToDTO(tiersRepository.save(existing));
    }

    @Override
    public void deleteTiers(Long id) {
        if (!tiersRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tiers", id);
        }
        tiersRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TiersDTO getTiersById(Long id) {
        return convertToDTO(tiersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tiers", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TiersDTO> getAllTiers(Pageable pageable) {
        return tiersRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TiersDTO> searchTiers(Map<String, Object> criteria) {
        return tiersRepository.findAll(TiersSpecification.avecCriteres(criteria))
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TiersDTO> findByNom(String nom) {
        return tiersRepository.findByNomContainingIgnoreCase(nom)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TiersDTO> findByPrenom(String prenom) {
        return tiersRepository.findByPrenomContainingIgnoreCase(prenom)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TiersDTO> findByNomAndPrenom(String nom, String prenom) {
        return tiersRepository.rechercherParNomPrenom(nom, prenom)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TiersDTO findByEmail(String email) {
        return tiersRepository.findByEmail(email).map(this::convertToDTO).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public TiersDTO findByMobile(String mobile) {
        return tiersRepository.findByMobile(mobile).map(this::convertToDTO).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TiersDTO> findByTypeTiers(String typeTiers) {
        return tiersRepository.findByTypeTiers(typeTiers)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
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
        TiersDTO dto = modelMapper.map(tiers, TiersDTO.class);

        // Mapper manuellement les comptes (évite les erreurs de proxy Hibernate)
        if (tiers.getAccounts() != null) {
            dto.setComptes(tiers.getAccounts().stream()
                    .map(c -> modelMapper.map(c, CompteBancaireDTO.class))
                    .collect(Collectors.toList()));
        }

        // Mapper manuellement les personnes en charge
        if (tiers.getPersonnes() != null) {
            dto.setPersonnesEnCharge(tiers.getPersonnes().stream()
                    .map(p -> modelMapper.map(p, PersonneEnChargeDTO.class))
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public Tiers convertToEntity(TiersDTO dto) {
        Tiers tiers = modelMapper.map(dto, Tiers.class);

        // Mapper comptes bancaires avec relation bidirectionnelle
        if (dto.getComptes() != null && !dto.getComptes().isEmpty()) {
            List<CompteBancaire> comptes = new ArrayList<>();
            for (CompteBancaireDTO cDto : dto.getComptes()) {
                CompteBancaire c = modelMapper.map(cDto, CompteBancaire.class);
                c.setTiers(tiers);
                comptes.add(c);
            }
            tiers.setAccounts(comptes);
        }

        // Mapper personnes en charge avec relation bidirectionnelle
        if (dto.getPersonnesEnCharge() != null
                && !dto.getPersonnesEnCharge().isEmpty()) {
            List<PersonneEnCharge> personnes = new ArrayList<>();
            for (PersonneEnChargeDTO pDto : dto.getPersonnesEnCharge()) {
                PersonneEnCharge p = modelMapper.map(pDto, PersonneEnCharge.class);
                p.setTiers(tiers);
                personnes.add(p);
            }
            tiers.setPersonnes(personnes);
        }

        return tiers;
    }

    @Override
    public Tiers getEntityById(Long id) {
        return tiersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tiers", id));
    }

    @Override
    public List<Tiers> searchEntities(Map<String, Object> criteria) {
        return tiersRepository.findAll(TiersSpecification.avecCriteres(criteria));
    }
}