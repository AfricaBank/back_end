package org.horizon.test.dao.repositories;

import org.horizon.test.dao.entities.EERPlus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EERPlusRepository extends JpaRepository<EERPlus, Long> {
}