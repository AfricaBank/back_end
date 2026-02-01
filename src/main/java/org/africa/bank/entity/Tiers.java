package org.africa.bank.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "tiers")
@Data
public class Tiers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* =========================
       Identification
    ========================= */
    private String typeTiers;
    private String categorieClient;
    private String matriculeAgent;

    private String civilite;
    private String sexe;

    private String nom;
    private String prenom;
    private String nomAbrege;

    private Date dateNaissance;
    private String lieuNaissance;
    private String paysNaissance;

    private String paysNationalite;
    private String paysDoubleNationalite;

    private String numeroIdentifiantFiscal;
    private String numeroImmatriculation;
    private String numeroActeNaissance;
    private String paysImmatriculation;

    /* =========================
       Informations familiales
    ========================= */
    private String situationFamille;
    private String regimeMatrimonial;
    private String nomMarital;

    private String prenomPere;
    private String prenomMere;
    private String nomJeuneFille;

    private Integer nombreEnfantsCharge;

    /* =========================
       Légal / KYC
    ========================= */
    private Date dateEer;
    private String motifEer;
    private String modaliteEer;
    private String paysKyc;

    private String capaciteJuridique;
    private Date dateEffet;
    private String segmentSecuriteFinanciere;

    /* =========================
       Activité professionnelle
    ========================= */
    private String categorieSocioProfessionnelle;
    private String secteurActiviteEconomique;
    private String activiteRisque;
    private String descriptionActivite;
    private Date dateCreationActivite;

    private String paysActivite;
    private Integer pourcentageActivite;
    private Integer cumulePourcentageActivite;
    private String commentaireActivite;

    /* =========================
       Employeur
    ========================= */
    private String nomEmployeur;
    private Boolean domiciliationSalaire;
    private Date depuisQuand;

    /* =========================
       Adresse & contact
    ========================= */
    private String codePostal;
    private String ville;
    private String adresse;

    private String mobile;
    private String email;

    private String paysAdresseFiscale;
    private String statutResidence;
    private Date dateEntreeTerritoire;

    /* =========================
       Relation & consentement
    ========================= */
    private String consentementCreditBureau;
    private String commentaireRelation;

    /* =========================
       Relations
    ========================= */

    @OneToMany(mappedBy = "tiers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompteBancaire> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "tiers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonneEnCharge> personnes = new ArrayList<>();
}
