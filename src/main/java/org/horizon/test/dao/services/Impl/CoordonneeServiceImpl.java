package org.horizon.test.dao.services.Impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.Coordonnee;
import org.horizon.test.dao.entities.Dossier;
import org.horizon.test.dao.repositories.CoordonneeRepository;
import org.horizon.test.dao.repositories.DossierRepository;
import org.horizon.test.dao.services.CoordonneeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@AllArgsConstructor
public class CoordonneeServiceImpl implements CoordonneeService {

    private CoordonneeRepository coordonneeRepository;
    private DossierRepository dossierRepository;

    @Override
    public Page<Coordonnee> getAllCoordonnees(int page, int size) {
        return coordonneeRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Coordonnee getCoordonnee(Long idCoordonnee) {
        return coordonneeRepository.findById(idCoordonnee).orElse(null);
    }

    @Override
    public Coordonnee addCoordonnee(Long idDossier, Coordonnee coordonnee) {
        Dossier dossier = dossierRepository.findById(idDossier).orElse(null);
        coordonnee.setDossier(dossier);
        return coordonneeRepository.save(coordonnee);
    }

    @Override
    public Coordonnee updateCoordonnee(Coordonnee coordonnee) {
        return coordonneeRepository.save(coordonnee);
    }
}
