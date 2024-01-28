package org.horizon.test.dao;

import org.horizon.test.entities.PersonneLPhysique;

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
