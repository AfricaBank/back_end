package org.africa.bank.service.workflow;

import org.africa.bank.dto.*;
import org.africa.bank.entity.DossierEER;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IWorkflowEERService {
    DossierEER getDossierById(Long id);
    Map<String, Object> getStatutWorkflow(Long dossierId);
    DossierEER abandonnerDossier(Long dossierId);
    DossierEER reprendreDossier(Long dossierId);
    DossierEER initierDossier(Map<String, Object> params);
    DossierEER selectionnerTiersExistant(Long dossierId, Long tiersId);
    DossierEER ajouterTitulaire(Long dossierId, TiersDTO tiersDTO, boolean estCoTitulaire);
    DossierEER ajouterLienPhysique(Long dossierId, PersonneLPhysiqueDTO dto);
    DossierEER ajouterLienMoral(Long dossierId, PersonneLMDTO dto);
    DossierEER confirmerPJCompletes(Long dossierId);
    DossierEER finaliserDossier(Long dossierId);
}
