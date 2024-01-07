package org.africa.bank.dao.repository;

import org.africa.bank.dao.entity.Tiers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitulaireRepository extends JpaRepository<Tiers, Long> {

    boolean existsById(Long id);

    boolean existsByNom(String nom);
}
