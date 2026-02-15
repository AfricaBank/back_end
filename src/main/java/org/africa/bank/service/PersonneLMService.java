package org.africa.bank.service;

import org.africa.bank.dto.PersonneLMDTO;
import org.africa.bank.entity.PersonneLM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PersonneLMService {

    PersonneLMDTO createPersonneMorale(PersonneLMDTO personneDTO);
    PersonneLMDTO updatePersonneMorale(Long id, PersonneLMDTO personneDTO);
    void deletePersonneMorale(Long id);
    PersonneLMDTO getPersonneMoraleById(Long id);
    Page<PersonneLMDTO> getAllPersonnesMorales(Pageable pageable);
    List<PersonneLMDTO> getPersonnesMoralesByDossierEER(Long dossierEERId);
    List<PersonneLMDTO> getPersonnesMoralesByTiersReference(Long tiersId);
    List<PersonneLMDTO> searchByRaisonSociale(String raisonSociale);
    List<PersonneLMDTO> findByFormeJuridique(String formeJuridique);
    PersonneLMDTO convertToDTO(PersonneLM personne);
    PersonneLM convertToEntity(PersonneLMDTO personneDTO);
}