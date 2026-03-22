package org.africa.bank.repository;

import org.africa.bank.entity.CRConseiller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CRConseillerRepository extends JpaRepository<CRConseiller, Long> {

    Optional<CRConseiller> findByDossierEERId(Long dossierId);

    boolean existsByDossierEERId(Long dossierId);
}