package org.africa.bank.service.impl;

import org.africa.bank.dto.PersonneLMDTO;
import org.africa.bank.entity.PersonneLM;
import org.africa.bank.repository.PersonneLMRepository;
import org.africa.bank.service.PersonneLMService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonneLMServiceImpl implements PersonneLMService {

    @Autowired
    private PersonneLMRepository personneRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PersonneLMDTO createPersonneMorale(PersonneLMDTO personneDTO) {
        PersonneLM personne = convertToEntity(personneDTO);
        personne = personneRepository.save(personne);
        return convertToDTO(personne);
    }

    @Override
    public PersonneLMDTO updatePersonneMorale(Long id, PersonneLMDTO personneDTO) {
        PersonneLM existingPersonne = personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne morale non trouvée avec l'id: " + id));

        modelMapper.map(personneDTO, existingPersonne);
        PersonneLM updatedPersonne = personneRepository.save(existingPersonne);
        return convertToDTO(updatedPersonne);
    }

    @Override
    public void deletePersonneMorale(Long id) {
        if (!personneRepository.existsById(id)) {
            throw new RuntimeException("Personne morale non trouvée avec l'id: " + id);
        }
        personneRepository.deleteById(id);
    }

    @Override
    public PersonneLMDTO getPersonneMoraleById(Long id) {
        PersonneLM personne = personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne morale non trouvée avec l'id: " + id));
        return convertToDTO(personne);
    }

    @Override
    public Page<PersonneLMDTO> getAllPersonnesMorales(Pageable pageable) {
        return personneRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<PersonneLMDTO> getPersonnesMoralesByDossierEER(Long dossierEERId) {
        return personneRepository.findByDossierEERId(dossierEERId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonneLMDTO> getPersonnesMoralesByTiersReference(Long tiersId) {
        return personneRepository.findByTiersReferenceId(tiersId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonneLMDTO> searchByRaisonSociale(String raisonSociale) {
        return personneRepository.rechercherParRaisonSociale(raisonSociale)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonneLMDTO> findByFormeJuridique(String formeJuridique) {
        return personneRepository.findByFormeJuridique(formeJuridique)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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