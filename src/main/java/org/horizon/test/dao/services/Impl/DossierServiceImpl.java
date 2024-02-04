package org.horizon.test.dao.services.Impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.*;
import org.horizon.test.dao.repositories.DossierRepository;
import org.horizon.test.dao.services.DossierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DossierServiceImpl implements DossierService {

    private DossierRepository dossierRepository;
    private BanqueServiceImpl banqueService;
    private CoordonneeServiceImpl coordonneeService;
    private EERServiceImpl eerService;
    private IdentificationServiceImpl identificationService;

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

//        Banque banque = new Banque();
//        banque.setIdBanque(null);
//        dossier.setBanque(banqueService.addBanque(banque));
//
//        Coordonnee coordonnee = new Coordonnee();
//        coordonnee.setIdCoordonnee(null);
//        dossier.setCoordonnee(coordonneeService.addCoordonnee(coordonnee));
//
//        EER eer = new EER();
//        eer.setIdEER(null);
//        dossier.setEer(eerService.addEER(eer));
//
//        Identification identification = new Identification();
//        coordonnee.setIdCoordonnee(null);
//        dossier.setIdentification(identificationService.addIdentification(identification));


//        dossier.getBanque().setDossier(dossier);
//        dossier.getCoordonnee().setDossier(dossier);
//        dossier.getEer().setDossier(dossier);
//        dossier.getIdentification().setDossier(dossier);
        return dossierRepository.save(dossier);
    }

    @Override
    public Dossier updateDossier(Dossier dossier) {
        return dossierRepository.save(dossier);
    }
}
