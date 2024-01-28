package org.horizon.test.dao;

import org.horizon.test.entities.PersonneLPhysique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnePhysiqueRepository extends JpaRepository<PersonneLPhysique, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}
