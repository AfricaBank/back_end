package org.horizon.test.dao.services;

import org.horizon.test.dao.entities.Dossier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DossierService {


    Page<Dossier> getAllDossiers(String codeExploitant, int page, int size);

    Dossier getDossier(Long idDossier);

    Dossier addDossier( Dossier dossier);

    Dossier updateDossier(Dossier dossier);
    
}
