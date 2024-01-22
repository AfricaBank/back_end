package org.horizon.test.dao.services.Impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.Titulaire;
import org.horizon.test.dao.repositories.TitulaireRepository;
import org.horizon.test.dao.services.TitulaireService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TitulaireServiceImpl implements TitulaireService {

    private TitulaireRepository titulaireRepository;

    @Override
    public Page<Titulaire> getAllTitulaires(String nom, int page, int size) {
        return titulaireRepository.findTitulairesByNomContains(nom, PageRequest.of(page, size));
    }

    @Override
    public Titulaire getTitulaire(Long idTitulaire) {
        return titulaireRepository.findById(idTitulaire).orElse(null);
    }

    @Override
    public Titulaire addTitulaire(Titulaire titulaire) {
        return titulaireRepository.save(titulaire);
    }

    @Override
    public Titulaire updateTitulaire(Titulaire titulaire) {
        return titulaireRepository.save(titulaire);
    }
}
