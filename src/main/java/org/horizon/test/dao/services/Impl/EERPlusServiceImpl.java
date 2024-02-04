package org.horizon.test.dao.services.Impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.Dossier;
import org.horizon.test.dao.entities.EERPlus;
import org.horizon.test.dao.repositories.DossierRepository;
import org.horizon.test.dao.repositories.EERPlusRepository;
import org.horizon.test.dao.services.EERPlusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EERPlusServiceImpl implements EERPlusService {

    private EERPlusRepository eerPlusRepository;
    private DossierRepository dossierRepository;

    @Override
    public Page<EERPlus> getAllEERPluss(int page, int size) {
        return eerPlusRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public EERPlus getEERPlus(Long idEERPlus) {
        return eerPlusRepository.findById(idEERPlus).orElse(null);
    }

    @Override
    public EERPlus addEERPlus(Long idDossier, EERPlus eer) {
        Dossier dossier = dossierRepository.findById(idDossier).orElse(null);
        eer.setDossier(dossier);
        return eerPlusRepository.save(eer);
    }

    @Override
    public EERPlus updateEERPlus(EERPlus eer) {
        return eerPlusRepository.save(eer);
    }
}
