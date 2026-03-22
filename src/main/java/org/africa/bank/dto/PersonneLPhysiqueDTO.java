package org.africa.bank.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO PersonneLPhysique — corrections :
 *  - motifEER : Date → String
 *  - codePostal / localite : Integer → String
 *  - codeSiege : Integer → String
 */
@Data
public class PersonneLPhysiqueDTO {
    private Long id;
    private String typePersonne;
    private String typeRelation;
    private String civilite;
    private String sexe;
    private String nomFamille;
    private String prenom;
    private String nomAbrege;
    private String nationalityPays;
    private String doubleNationalityPays;
    private Boolean residentUs;
    private String typeDocumentIdentification;
    private String numeroDocument;
    private String paysEmissionDocument;
    private String nin;
    private String categorieClientele;
    private LocalDate dateEER;
    private String modalite;
    private String motifEER;            // CORRECTION : String (pas Date)
    private LocalDate dateDeNaissance;
    private String lieuNaissance;
    private String paysNaissance;
    private String nomMere;
    private String prenomPere;
    private String prenomMere;
    private Boolean naissancePresumee;
    private String situationFamiliale;
    private String regimeMatrimoniale;
    private String nomMarital;
    private String enfantEnCharge;
    private String numTelephone;
    private String adresseFiscale;
    private String codePostal;          // CORRECTION : String (pas Integer)
    private String localite;            // CORRECTION : String (pas Integer)
    private String pays;
    private Boolean statutResidence;
    private String wilaya;
    private String commune;
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
    private String detectionPPE;
    private String commentaire;
    private String typePPE;
    private Boolean ppeLocale;
    private LocalDate dateIdentification;
    private LocalDate dateInterrogationVigilance;
    private Boolean sousSanction;
    private String idNational;
    private String codeSiege;           // CORRECTION : String (pas Integer)
    private Long racine;
    private String segmentClientele;

    // Références
    private Long dossierEERId;
    private Long tiersReferenceId;
}
 