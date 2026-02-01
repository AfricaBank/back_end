package org.africa.bank.repository;

import org.africa.bank.entity.Tiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TiersRepository extends JpaRepository<Tiers, Long>, JpaSpecificationExecutor<Tiers> {

    Optional<Tiers> findByNomAndPrenomAndDateNaissance(
            String nom, String prenom, Date dateNaissance);

    List<Tiers> findByNomContainingIgnoreCase(String nom);

    List<Tiers> findByPrenomContainingIgnoreCase(String prenom);

    List<Tiers> findByNumeroIdentifiantFiscal(String numeroIdentifiantFiscal);

    @Query("SELECT t FROM Tiers t WHERE t.nom LIKE %:nom% AND t.prenom LIKE %:prenom%")
    List<Tiers> rechercherParNomPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

    @Query("SELECT t FROM Tiers t WHERE t.email = :email")
    Optional<Tiers> findByEmail(@Param("email") String email);

    @Query("SELECT t FROM Tiers t WHERE t.mobile = :mobile")
    Optional<Tiers> findByMobile(@Param("mobile") String mobile);

    List<Tiers> findByTypeTiers(String typeTiers);

    List<Tiers> findByNomAndPrenom(String nom, String prenom);
}