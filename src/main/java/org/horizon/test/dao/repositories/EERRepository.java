package org.horizon.test.dao.repositories;

import org.horizon.test.dao.entities.EER;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EERRepository extends JpaRepository<EER, Long> {

    Page<EER> findEERByMotifEERContains(String motif, Pageable pageable);

}