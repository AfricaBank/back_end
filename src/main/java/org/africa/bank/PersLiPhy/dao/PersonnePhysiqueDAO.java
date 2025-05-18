package org.africa.bank.PersLiPhy.dao;

import org.africa.bank.PersLiPhy.entity.PersonneLPhysique;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface PersonnePhysiqueDAO {
    Optional<PersonneLPhysique> getPersonneLPhysique(Long id);

    PersonneLPhysique savePersonneLPhysique(PersonneLPhysique personneLPhysique);

    PersonneLPhysique getPersonneLPhysiqueById(Long id);

    List<PersonneLPhysique> getAllPersonnesLPhysiques();

    PersonneLPhysique updatePersonneLPhysique(PersonneLPhysique personneLPhysique);

    void deletePersonneLPhysique(Long id);
}
