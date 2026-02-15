package org.africa.bank.repository;

import org.africa.bank.entity.DossierEER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DossierEERRepository extends JpaRepository<DossierEER, Long> {

    Optional<DossierEER> findByReferenceDossier(String reference);

    @Query("SELECT d FROM DossierEER d WHERE d.statut = 'EN_COURS'")
    List<DossierEER> findDossiersEnCours();

    @Query("SELECT d FROM DossierEER d WHERE d.createur = :createur")
    List<DossierEER> findByCreateur(@Param("createur") String createur);

    @Query("SELECT d FROM DossierEER d WHERE d.titulairePrincipal.id = :tiersId OR " +
            "EXISTS (SELECT 1 FROM d.coTitulaires c WHERE c.id = :tiersId)")
    List<DossierEER> findByTiersId(@Param("tiersId") Long tiersId);
}