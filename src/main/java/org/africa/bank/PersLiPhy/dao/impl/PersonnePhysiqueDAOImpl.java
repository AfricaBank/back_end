package org.africa.bank.PersLiPhy.dao.impl;

import jakarta.transaction.Transactional;
import org.africa.bank.PersLiPhy.entity.PersonneLPhysique;
import org.africa.bank.PersLiPhy.dao.repository.PersonnePhysiqueRepository;
import org.africa.bank.PersLiPhy.dao.PersonnePhysiqueDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonnePhysiqueDAOImpl implements PersonnePhysiqueDAO {

    private final PersonnePhysiqueRepository personneLPhysiqueRepository;

    public PersonnePhysiqueDAOImpl(PersonnePhysiqueRepository personneLPhysiqueRepository) {
        this.personneLPhysiqueRepository = personneLPhysiqueRepository;
    }

    @Transactional
    @Override
    public Optional<PersonneLPhysique> getPersonneLPhysique(Long id) {
        return personneLPhysiqueRepository.findById(id);
    }

    @Transactional
    @Override
    public PersonneLPhysique savePersonneLPhysique(PersonneLPhysique personneLPhysique) {
        return personneLPhysiqueRepository.save(personneLPhysique);
    }

    @Transactional
    @Override
    public PersonneLPhysique getPersonneLPhysiqueById(Long id) {
        return personneLPhysiqueRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public List<PersonneLPhysique> getAllPersonnesLPhysiques() {
        return personneLPhysiqueRepository.findAll();
    }

    @Transactional
    @Override
    public PersonneLPhysique updatePersonneLPhysique(PersonneLPhysique personneLPhysique) {
        return personneLPhysiqueRepository.save(personneLPhysique);
    }

    @Transactional
    @Override
    public void deletePersonneLPhysique(Long id) {
        personneLPhysiqueRepository.deleteById(id);
    }
}
