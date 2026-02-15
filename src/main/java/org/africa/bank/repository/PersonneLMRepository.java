package org.africa.bank.repository;

import org.africa.bank.entity.PersonneLM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonneLMRepository extends JpaRepository<PersonneLM, Long> {

    List<PersonneLM> findByDossierEERId(Long dossierEERId);

    @Query("SELECT p FROM PersonneLM p WHERE p.tiers.id = :tiersId")
    List<PersonneLM> findByTiersReferenceId(Long tiersId);

    @Query("SELECT p FROM PersonneLM p WHERE p.raisonSociale LIKE %:raisonSociale%")
    List<PersonneLM> rechercherParRaisonSociale(@Param("raisonSociale") String raisonSociale);

    List<PersonneLM> findByFormeJuridique(String formeJuridique);
}