package org.horizon.test.dao.repositories;

import org.horizon.test.dao.entities.Coordonnee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CoordonneeRepository extends JpaRepository<Coordonnee, Long> {
}