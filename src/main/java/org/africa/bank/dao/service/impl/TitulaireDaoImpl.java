package org.africa.bank.dao.service.impl;

import org.africa.bank.dao.service.TitulaireDao;
import org.africa.bank.dao.entity.Tiers;
import org.africa.bank.dao.repository.TitulaireRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TitulaireDaoImpl implements TitulaireDao {

    private final TitulaireRepository titulaireRepository;

    public TitulaireDaoImpl(TitulaireRepository titulaireRepository) {
        this.titulaireRepository = titulaireRepository;
    }

    @Override
    public Optional<Tiers> getTitulaire(Long id) {
        return titulaireRepository.findById(id);
    }

    @Override
    public Tiers saveTier(Tiers tiers) {
        return titulaireRepository.save(tiers);
    }

    @Override
    public Tiers getTiersById(Long id) {
        return titulaireRepository.findById(id).get();
    }

    @Override
    public List<Tiers> getAll() {
        return titulaireRepository.findAll();
    }

    @Override
    public Tiers updateTitulaire(Tiers titulaire) {
        return null;
    }

    @Override
    public void deleteTitulaire(Long id) {

    }

    @Override
    public Page<Tiers> getAllPageable(Pageable pageable) {
        return null;
    }
}
