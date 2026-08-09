package org.africa.bank.repository;

import org.africa.bank.entity.AvisDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AvisDecisionRepository
        extends JpaRepository<AvisDecision, Long> {
    Optional<AvisDecision> findByDossierEERId(Long dossierId);
}