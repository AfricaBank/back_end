package org.africa.bank.service.workflow;

import org.africa.bank.repository.DossierEERRepository;
import org.africa.bank.repository.TiersRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Couche transactionnelle séparée — évite le proxy CGLIB sur WorkflowEERService.
 */
@Component
@Transactional
public class WorkflowTransactionHelper {

    private final DossierEERRepository dossierRepository;
    private final TiersRepository tiersRepository;

    public WorkflowTransactionHelper(
            DossierEERRepository dossierRepository,
            TiersRepository tiersRepository) {
        this.dossierRepository = dossierRepository;
        this.tiersRepository   = tiersRepository;
    }

    public DossierEERRepository getDossierRepository() { return dossierRepository; }
    public TiersRepository getTiersRepository()        { return tiersRepository; }
}