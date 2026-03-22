package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Personne liée physique (Mandataire, Garant PP, Représentant Légal).
 * Correction : suppression des @Temporal sur LocalDate (incompatible).
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personnes_physiques")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PersonneLPhysique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typePersonne;
    private String typeRelation;   // MANDATAIRE | GARANT_PP | REPRESENTANT_LEGAL

    // ── État civil ───────────────────────────────────────────────────────────
    private String civilite;
    private String sexe;

    @Column(name = "nom_famille", length = 50)
    private String nomFamille;

    @Column(length = 50)
    private String prenom;

    @Column(length = 25)
    private String nomAbrege;

    private String nationalityPays;
    private String doubleNationalityPays;
    private Boolean residentUs;
    private String typeDocumentIdentification;

    @Column(length = 20)
    private String numeroDocument;

    private String paysEmissiondocument;

    @Column(length = 18)
    private String nin;

    // ── EER / KYC ────────────────────────────────────────────────────────────
    private String categorieClientele;
    private LocalDate dateEER;
    private String modalite;
    private String motifEER;        // CORRECTION : String, pas Date

    // ── Naissance ────────────────────────────────────────────────────────────
    private LocalDate datedeNaissance;
    private String lieuNaissance;
    private String paysNaissance;

    @Column(length = 50)
    private String nomMere;

    @Column(length = 35)
    private String prenomPere;

    @Column(length = 50)
    private String prenomMere;

    private Boolean naissancePresumee;

    // ── Situation familiale ───────────────────────────────────────────────────
    private String situationFamiliale;
    private String regimeMatrimoniale;

    @Column(length = 35)
    private String nomMarital;

    private String enfantEnCharge;

    // ── Coordonnées ──────────────────────────────────────────────────────────
    @Column(name = "num_telephone", length = 20)
    private String numTelephone;

    @Column(length = 35)
    private String adresseFiscale;

    @Column(length = 12)
    private String codePostal;      // CORRECTION : String (pas Integer)

    @Column(length = 35)
    private String localite;        // CORRECTION : String (pas Integer)

    private String pays;
    private Boolean statutResidence;
    private String wilaya;
    private String commune;

    // ── Activité professionnelle ──────────────────────────────────────────────
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
    private LocalDate dateCreationActivite;
    private String principalPaysActivite;
    private String activiteRisque;
    private String indicateurProfessionnel;
    private String codeSectoriel;
    private Integer avoirsControles;
    private String libelle;
    private Integer pourcentage;
    private Integer pourcentageDetection;

    // ── Conformité ───────────────────────────────────────────────────────────
    private String detectionPPE;

    @Column(length = 250)
    private String commentaire;

    private String typePPE;
    private Boolean ppeLocale;
    private LocalDate dateIdentification;
    private LocalDate dateInterrogationVigilance;
    private Boolean sousSanction;

    // ── Relation bancaire ────────────────────────────────────────────────────
    @Column(length = 13)
    private String idNational;

    @Column(length = 5)
    private String codeSiege;       // CORRECTION : String (format bancaire)

    private Long racine;
    private String segmentClientele;

    // ── Relations JPA ────────────────────────────────────────────────────────
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id")
    private DossierEER dossierEER;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tiers_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Tiers tiers;
}
