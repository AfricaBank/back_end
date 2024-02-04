package org.horizon.test.dao.services.Impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.Banque;
import org.horizon.test.dao.entities.Dossier;
import org.horizon.test.dao.repositories.BanqueRepository;
import org.horizon.test.dao.repositories.DossierRepository;
import org.horizon.test.dao.services.BanqueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BanqueServiceImpl implements BanqueService {

    private BanqueRepository banqueRepository;
    private DossierRepository dossierRepository;

    @Override
    public Page<Banque> getAllBanques(int page, int size) {
        return banqueRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Banque getBanque(Long idBanque) {
        return banqueRepository.findById(idBanque).orElse(null);
    }

    @Override
    public Banque addBanque(Long idDossier, Banque banque) {
        Dossier dossier = dossierRepository.findById(idDossier).orElse(null);
        banque.setDossier(dossier);
        return banqueRepository.save(banque);
    }

    @Override
    public Banque updateBanque(Banque banque) {
        return banqueRepository.save(banque);
    }
}
