package org.africa.bank.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "personnes_physiques") // Convention : minuscules et pluriel
public class PersonneLPhysique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typePersonne;

    // Champ requis par votre Repository (findByTypeRelation)
    private String typeRelation;

    /* =========================
       Identification & État Civil
    ========================= */
    private String civilite;
    private String sexe;

    @Column(name = "nom_famille")
    private String nomFamille; // Corrigé pour correspondre au Repository

    @Column(length=50)
    private String prenom;

    @Column(length=50)
    private String nomAbrege;

    private String nationalityPays;
    private String doubleNationalityPays;
    private Boolean residentUs;
    private String typeDocumentIdentification;
    private String numeroDocument;
    private String paysEmissiondocument;
    private String nin;

    /* =========================
       Rubrique EER / KYC
    ========================= */
    private String categorieClientele;

    @Temporal(TemporalType.DATE)
    private Date dateEER;

    private String modalite;
    private String motifEER; // Changé de Date à String (un motif est un texte)

    @Temporal(TemporalType.DATE)
    private Date datedeNaissance;

    private String lieuNaissance;
    private String paysNaissance;
    private String nomMere;
    private String prenomPere;
    private String prenomMere;
    private Boolean naissancePresumee;

    /* =========================
       Situation Familiale
    ========================= */
    private String situationFamiliale;
    private String regimeMatrimoniale;
    private String nomMarital;
    private String enfantEnCharge;

    /* =========================
       Coordonnées & Adresse
    ========================= */
    private String numTelephone; // Changé int -> String pour garder le 0 initial
    private String adresseFiscale;
    private String codePostal;   // Changé int -> String
    private String localite;     // Changé int -> String
    private String pays;
    private Boolean statutResidence; // Corrigé l'accent (résidence -> residence)
    private String wilaya;
    private String commune;

    /* =========================
       Activité Professionnelle
    ========================= */
    private String categorieClienteleM;
    private String categorieSocioProfessionnelle;
    private Boolean connaissanceInterne;
    private Boolean dirigeantBE;
    private Boolean beSociete;
    private Boolean detentionCompteTitre;
    private Boolean delegationKYC;
    private Boolean presenceFlux;
    private String secteurActiviteEconomique;
    private String libelleAPE;

    @Temporal(TemporalType.DATE)
    private Date dateCreationActivite;

    private String principalPaysActivite;
    private String activiteRisque;
    private String indicateurProfessionnel;
    private Integer codeSectoriel;
    private Integer avoirsControles;
    private String libelle;
    private Integer pourcentage;
    private Integer pourcentageDetection;

    /* =========================
       Conformité & Sanctions
    ========================= */
    private String detectionPPE;
    private String commentaire;
    private String typePPE;
    private Boolean ppeLocale;

    @Temporal(TemporalType.DATE)
    private Date dateIdentification;

    @Temporal(TemporalType.DATE)
    private Date dateInterrogationVigilance;

    private Boolean sousSanction;

    /* =========================
       Relation Bancaire
    ========================= */
    private String idNational;
    private Integer codeSiege;
    private Integer racine;
    private String segmentClientele;

    /* =========================
       Relations (Jointures)
    ========================= */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id")
    private DossierEER dossierEER;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tiers_id")
    private Tiers tiers;
}