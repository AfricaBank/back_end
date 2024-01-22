package org.horizon.test.dao.repositories;

import org.horizon.test.dao.entities.Banque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanqueRepository extends JpaRepository<Banque, Long> {

    Page<Banque> findBanqueByCodeSiegeContains(Integer codeSiege, Pageable pageable);

}