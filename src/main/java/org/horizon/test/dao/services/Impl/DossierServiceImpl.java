package org.horizon.test.dao.services.Impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.Dossier;
import org.horizon.test.dao.repositories.DossierRepository;
import org.horizon.test.dao.services.DossierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DossierServiceImpl implements DossierService {

    private DossierRepository dossierRepository;

    @Override
    public Page<Dossier> getAllDossiers(String codeExploitant, int page, int size) {
        return dossierRepository.findDossiersByCodeExploitantContains(codeExploitant, PageRequest.of(page, size));
    }

    @Override
    public Dossier getDossier(Long idDossier) {
        return dossierRepository.findById(idDossier).orElse(null);
    }

    @Override
    public Dossier addDossier(Dossier dossier) {
        return dossierRepository.save(dossier);
    }

    @Override
    public Dossier updateDossier(Dossier dossier) {
        return dossierRepository.save(dossier);
    }
}
