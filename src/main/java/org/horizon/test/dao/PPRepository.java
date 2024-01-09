package org.horizon.test.dao;

import org.horizon.test.entities.PersonneLPhysique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="personnephysiques",itemResourceRel="personnephysique")
public interface PPRepository extends JpaRepository<PersonneLPhysique, Long> {

}
