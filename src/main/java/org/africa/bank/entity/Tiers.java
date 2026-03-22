package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité Tiers — représente un client (PP ou PM) dans le système.
 * Toutes les dates utilisent LocalDate (cohérence avec Java 8+).
 */
@Entity
@Table(name = "tiers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tiers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Identification ────────────────────────────────────────────────────────
    private String typeTiers;          // PHYSIQUE | MORALE
    private String categorieClient;
    private String matriculeAgent;

    private String civilite;
    private String sexe;

    @Column(length = 50)
    private String nom;

    @Column(length = 50)
    private String prenom;

    @Column(length = 25)
    private String nomAbrege;

    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String paysNaissance;

    private String paysNationalite;
    private String paysDoubleNationalite;

    @Column(length = 25)
    private String numeroIdentifiantFiscal;

    @Column(length = 25)
    private String numeroImmatriculation;

    @Column(length = 25)
    private String numeroActeNaissance;

    private String paysImmatriculation;

    // ── Informations familiales ───────────────────────────────────────────────
    private String situationFamille;
    private String regimeMatrimonial;

    @Column(length = 35)
    private String nomMarital;

    @Column(length = 35)
    private String prenomPere;

    @Column(length = 50)
    private String prenomMere;

    @Column(length = 50)
    private String nomJeuneFille;

    private Integer nombreEnfantsCharge;

    // ── KYC / EER ────────────────────────────────────────────────────────────
    private LocalDate dateEer;
    private String motifEer;
    private String modaliteEer;
    private String paysKyc;
    private String capaciteJuridique;
    private LocalDate dateEffet;
    private String segmentSecuriteFinanciere;

    // ── Activité professionnelle ──────────────────────────────────────────────
    private String categorieSocioProfessionnelle;
    private String secteurActiviteEconomique;
    private String activiteRisque;

    @Column(length = 255)
    private String descriptionActivite;

    private LocalDate dateCreationActivite;
    private String paysActivite;
    private Integer pourcentageActivite;
    private Integer cumulePourcentageActivite;

    @Column(length = 500)
    private String commentaireActivite;

    // ── Employeur ────────────────────────────────────────────────────────────
    @Column(length = 32)
    private String nomEmployeur;

    private Boolean domiciliationSalaire;
    private LocalDate depuisQuand;

    // ── Adresse & contact ────────────────────────────────────────────────────
    @Column(length = 12)
    private String codePostal;

    private String ville;

    @Column(length = 35)
    private String adresse;

    @Column(length = 20)
    private String mobile;

    private String email;

    private String paysAdresseFiscale;
    private String statutResidence;     // RESIDENT | NON_RESIDENT
    private LocalDate dateEntreeTerritoire;
    private LocalDate dateSortieTerritoire;

    // ── Consentement & relation ───────────────────────────────────────────────
    private String consentementCreditBureau;

    @Column(length = 1500)
    private String commentaireRelation;

    // ── Relations ────────────────────────────────────────────────────────────
    @JsonIgnore
    @OneToMany(mappedBy = "tiers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompteBancaire> accounts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tiers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonneEnCharge> personnes = new ArrayList<>();
}
