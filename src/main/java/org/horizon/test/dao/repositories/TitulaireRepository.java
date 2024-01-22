package org.horizon.test.dao.repositories;

import org.horizon.test.dao.entities.Titulaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitulaireRepository extends JpaRepository<Titulaire, Long> {

    Page<Titulaire> findTitulairesByNomContains(String nom, Pageable page);

}
