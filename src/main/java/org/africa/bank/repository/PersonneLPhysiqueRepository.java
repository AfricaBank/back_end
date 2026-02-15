package org.africa.bank.repository;

import org.africa.bank.entity.PersonneLPhysique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonneLPhysiqueRepository extends JpaRepository<PersonneLPhysique, Long> {

    List<PersonneLPhysique> findByDossierEERId(Long dossierEERId);

    @Query("SELECT p FROM PersonneLPhysique p WHERE p.tiers.id = :tiersId")
    List<PersonneLPhysique> findByTiersReferenceId(Long tiersId);

    @Query("SELECT p FROM PersonneLPhysique p WHERE p.nomFamille LIKE %:nom% AND p.prenom LIKE %:prenom%")
    List<PersonneLPhysique> rechercherParNomPrenom(
            @Param("nom") String nom, @Param("prenom") String prenom);

    List<PersonneLPhysique> findByTypeRelation(String typeRelation);
}