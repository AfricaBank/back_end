package org.horizon.test.dao.services;

import org.horizon.test.dao.entities.Titulaire;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TitulaireService  {

    Page<Titulaire> getAllTitulaires(String nom, int page, int size);

    Titulaire getTitulaire(Long idTitulaire);

    Titulaire addTitulaire( Titulaire titulaire);

    Titulaire updateTitulaire(Titulaire titulaire);

}
