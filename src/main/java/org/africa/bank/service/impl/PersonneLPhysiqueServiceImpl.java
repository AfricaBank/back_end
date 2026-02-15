package org.africa.bank.service.impl;

import org.africa.bank.dto.PersonneLPhysiqueDTO;
import org.africa.bank.entity.PersonneLPhysique;
import org.africa.bank.repository.PersonneLPhysiqueRepository;
import org.africa.bank.service.PersonneLPhysiqueService;
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
public class PersonneLPhysiqueServiceImpl implements PersonneLPhysiqueService {

    @Autowired
    private PersonneLPhysiqueRepository personneRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PersonneLPhysiqueDTO createPersonne(PersonneLPhysiqueDTO personneDTO) {
        PersonneLPhysique personne = convertToEntity(personneDTO);
        personne = personneRepository.save(personne);
        return convertToDTO(personne);
    }

    @Override
    public PersonneLPhysiqueDTO updatePersonne(Long id, PersonneLPhysiqueDTO personneDTO) {
        PersonneLPhysique existingPersonne = personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée avec l'id: " + id));

        modelMapper.map(personneDTO, existingPersonne);
        PersonneLPhysique updatedPersonne = personneRepository.save(existingPersonne);
        return convertToDTO(updatedPersonne);
    }

    @Override
    public void deletePersonne(Long id) {
        if (!personneRepository.existsById(id)) {
            throw new RuntimeException("Personne non trouvée avec l'id: " + id);
        }
        personneRepository.deleteById(id);
    }

    @Override
    public PersonneLPhysiqueDTO getPersonneById(Long id) {
        PersonneLPhysique personne = personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée avec l'id: " + id));
        return convertToDTO(personne);
    }

    @Override
    public Page<PersonneLPhysiqueDTO> getAllPersonnes(Pageable pageable) {
        return personneRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<PersonneLPhysiqueDTO> getPersonnesByDossierEER(Long dossierEERId) {
        return personneRepository.findByDossierEERId(dossierEERId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonneLPhysiqueDTO> getPersonnesByTiersReference(Long tiersId) {
        return personneRepository.findByTiersReferenceId(tiersId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonneLPhysiqueDTO> searchByNomPrenom(String nom, String prenom) {
        return personneRepository.rechercherParNomPrenom(nom, prenom)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonneLPhysiqueDTO convertToDTO(PersonneLPhysique personne) {
        return modelMapper.map(personne, PersonneLPhysiqueDTO.class);
    }

    @Override
    public PersonneLPhysique convertToEntity(PersonneLPhysiqueDTO personneDTO) {
        return modelMapper.map(personneDTO, PersonneLPhysique.class);
    }
}