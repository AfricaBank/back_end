package org.africa.bank.dao.service;

import org.africa.bank.dao.entity.Tiers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TitulaireDao {

    Optional<Tiers> getTitulaire(Long id);

    Tiers saveTier(Tiers tiers);

    Tiers getTiersById (Long id);

    List<Tiers> getAll();



    Tiers updateTitulaire(Tiers titulaire);

    void deleteTitulaire(Long id);

    Page<Tiers> getAllPageable(Pageable pageable);
}
