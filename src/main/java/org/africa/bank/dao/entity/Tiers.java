package org.africa.bank.dao.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "tiers")
@Data
public class Tiers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "typeTier")
    private String typeTier;
    // Rubrique vigilance
    private Date date_vigilance;
    private String presencelist;
    private String typeppe;
    private String ppeLocal;
    private boolean sanction;

    //Rubrique centrale des incident financière
    private Date dateInterrogationCip;
    private String detectionCip;

    //Identification
    private String categorieClient;
    private String matriculeAgent;
    private String civilite;
    private String sexe;
    private String nom;
    private String prenom;
    private String nomAbrege;
    private String paysNationalite;
    private String paysDoubleNationalite;
    private String paysImmatriculation;
    private String numeroIdentifiantFiscal; //NFI
    private String numeroImmatriculation;
    private String enseigneCommercial;
    private String numeroActeNaissance;

    //Rubrique Origine
    private String segmentSecuriteFinancière;
    private Date dateEer;
    private String profilClient;
    private String motifEer;
    private String modaliteEer;
    private String paysRealisationKyc;

    // Rubrique Naissance

    private Date dateNaissance;
    private String LieuNaissance;
    private String paysNaissance;
    private String prenomPere;
    private String nomJeuneFille;
    private String prenomMere;

    // rubrique Situation familialle
    private String situationFamille;
    private String nomMarial;
    private String regimeMatrimonial;

    //Rubrique Enfant et personnes prise en charge
    private int nombreEnfantEnCharge;
    private int nomEnfant;
    private int prenomEnfant;
    private Date dateNaissanceEnfant;
    private String sexeEnfant;

    private int nombrePersonne;
    private int nomPersone;
    private int prenomPersonne;
    private Date dateNaissancePersonne;
    private String sexePersonne;

    //Xapacité juridique
    private String type;
    private Date dateEffet;

    // Info crédit info bureau
    private String consentementCreditBureau;
    private String compteContribuable;
    private Date dateRecuiel;

    // Adresse fiscale
    private String ligneFiscal1;
    private String ligneFiscal2;
    private String ligneFiscal3;
    private String ligneFiscal4;
    private String codePostal;
    private String localite;
    private String paysAdressFiscale;
    private Date dateEntreEnTerritoire;
    private String statutResidence;
    private String SituationLogement;

    // Rubrique téléphone & Email
    private String domicil1;
    private String domicil2;
    private String portable1;
    private String portable2;
    private String email;

    //Rubrique activité professionnel
    // Sous rubrique Activité
    private String categorieSocioProfessionnelle;
    private String secteurActiviteEconomique;
    private String activiteRisque;
    private String descriptionActivite;
    private Date dateCreationActivite;
    // Sous rubrique Activité en % par Pays
    private String paysActivite;
    private int pourcentActivite;
    private int cumulePourcentActivite;
    private String commentaireActivite;

    // Ruvrique Information financière
    private Double totalBilan;
    private Double chifreAffaire;
    private Double resultanet;

    // Ruvrique Employeur
    private String nomEmployeur;
    private boolean domiciliationSalaire;
    private Date depuisQuand;

    // Ruvrique Relation Bancaire
    private String idNational;
    private String codeSectoriel;
    private String comantaireRelation1;
    // Sous rubrique Type de compte à ouvrir
    private String typeCompe;
    private String devise;
    private String motifOuverture;
    private String siege;
    private String racine;
    private String ordinal;
    private String cle;
    private String cleRib;
    private String conventionCompte;
    private String cartonSignature;

    // Sous rubrique profil Client



    // Rubrique Adresse courrier
//    private String ligneCourrier1;
//    private String ligneCourrier2;
//    private String ligneCourrier3;
//    private String ligneCourrier4;


}
