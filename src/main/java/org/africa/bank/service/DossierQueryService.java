package org.africa.bank.service;

import org.africa.bank.entity.DossierEER;
import org.africa.bank.repository.DossierEERRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de lecture des dossiers — séparé de WorkflowEERService
 * pour éviter les conflits de proxy Spring CGLIB liés à @Transactional.
 *
 * Même pattern que RecherchePersonneService.
 */
@Service
public class DossierQueryService {

    private final DossierEERRepository dossierRepository;

    public DossierQueryService(DossierEERRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    public Page<DossierEER> getAllDossiers(Pageable pageable) {
        return dossierRepository.findAll(pageable);
    }

    public List<DossierEER> getDossiersByCreateur(String createur) {
        return dossierRepository.findByCreateur(createur);
    }
}