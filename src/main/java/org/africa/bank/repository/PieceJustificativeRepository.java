package org.africa.bank.repository;

import org.africa.bank.entity.PieceJustificative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PieceJustificativeRepository
        extends JpaRepository<PieceJustificative, Long> {

    List<PieceJustificative> findByDossierEERId(Long dossierId);

    @Query("SELECT p FROM PieceJustificative p " +
            "WHERE p.dossierEER.id = :dossierId AND p.obligatoire = true " +
            "AND (p.attache = false OR p.attache IS NULL)")
    List<PieceJustificative> findPJObligatoiresNonAttachees(
            @Param("dossierId") Long dossierId);

    @Query("SELECT COUNT(p) = 0 FROM PieceJustificative p " +
            "WHERE p.dossierEER.id = :dossierId AND p.obligatoire = true " +
            "AND (p.attache = false OR p.attache IS NULL)")
    boolean toutesLesPJObligatoiresSontAttachees(
            @Param("dossierId") Long dossierId);
}