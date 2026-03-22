package org.africa.bank.service.impl;

import org.africa.bank.dto.PersonneLMDTO;
import org.africa.bank.entity.PersonneLM;
import org.africa.bank.exception.ResourceNotFoundException;
import org.africa.bank.repository.PersonneLMRepository;
import org.africa.bank.service.PersonneLMService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonneLMServiceImpl implements PersonneLMService {

    // CORRECTION : injection par constructeur
    private final PersonneLMRepository personneRepository;
    private final ModelMapper modelMapper;

    public PersonneLMServiceImpl(PersonneLMRepository personneRepository,
                                 ModelMapper modelMapper) {
        this.personneRepository = personneRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PersonneLMDTO createPersonneMorale(PersonneLMDTO personneDTO) {
        PersonneLM personne = convertToEntity(personneDTO);
        return convertToDTO(personneRepository.save(personne));
    }

    @Override
    public PersonneLMDTO updatePersonneMorale(Long id, PersonneLMDTO personneDTO) {
        PersonneLM existing = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personne morale", id));
        modelMapper.map(personneDTO, existing);
        return convertToDTO(personneRepository.save(existing));
    }

    @Override
    public void deletePersonneMorale(Long id) {
        if (!personneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Personne morale", id);
        }
        personneRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonneLMDTO getPersonneMoraleById(Long id) {
        return convertToDTO(personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personne morale", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonneLMDTO> getAllPersonnesMorales(Pageable pageable) {
        return personneRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonneLMDTO> getPersonnesMoralesByDossierEER(Long dossierEERId) {
        return personneRepository.findByDossierEERId(dossierEERId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonneLMDTO> getPersonnesMoralesByTiersReference(Long tiersId) {
        return personneRepository.findByTiersReferenceId(tiersId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonneLMDTO> searchByRaisonSociale(String raisonSociale) {
        return personneRepository.rechercherParRaisonSociale(raisonSociale)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonneLMDTO> findByFormeJuridique(String formeJuridique) {
        return personneRepository.findByFormeJuridique(formeJuridique)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PersonneLMDTO convertToDTO(PersonneLM personne) {
        return modelMapper.map(personne, PersonneLMDTO.class);
    }

    @Override
    public PersonneLM convertToEntity(PersonneLMDTO personneDTO) {
        return modelMapper.map(personneDTO, PersonneLM.class);
    }
}