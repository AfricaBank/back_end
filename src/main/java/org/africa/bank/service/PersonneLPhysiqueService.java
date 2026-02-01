package org.africa.bank.service;

import org.africa.bank.dto.PersonneLPhysiqueDTO;
import org.africa.bank.entity.PersonneLPhysique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PersonneLPhysiqueService {

    PersonneLPhysiqueDTO createPersonne(PersonneLPhysiqueDTO personneDTO);
    PersonneLPhysiqueDTO updatePersonne(Long id, PersonneLPhysiqueDTO personneDTO);
    void deletePersonne(Long id);
    PersonneLPhysiqueDTO getPersonneById(Long id);
    Page<PersonneLPhysiqueDTO> getAllPersonnes(Pageable pageable);
    List<PersonneLPhysiqueDTO> getPersonnesByDossierEER(Long dossierEERId);
    List<PersonneLPhysiqueDTO> getPersonnesByTiersReference(Long tiersId);
    List<PersonneLPhysiqueDTO> searchByNomPrenom(String nom, String prenom);
    PersonneLPhysiqueDTO convertToDTO(PersonneLPhysique personne);
    PersonneLPhysique convertToEntity(PersonneLPhysiqueDTO personneDTO);
}