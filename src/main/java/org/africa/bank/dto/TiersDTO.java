package org.africa.bank.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO Tiers — aligné exactement sur mapFormToTiersDTO() dans CreationTiers.tsx.
 *
 * Champs front → champs DTO :
 *   tiers                        → typeTiers
 *   categorie_cliente            → categorieClient
 *   matricule_agent              → matriculeAgent
 *   nom                          → nom
 *   prenom                       → prenom
 *   nom_abrege                   → nomAbrege
 *   civilite                     → civilite
 *   sexe                         → sexe
 *   date_naissance               → dateNaissance
 *   lieu_naissance               → lieuNaissance
 *   pays_naissance               → paysNaissance
 *   numero_identification_fiscale → numeroIdentifiantFiscal
 *   nationalite                  → paysNationalite
 *   double_nationalite           → paysDoubleNationalite
 *   situation_famille            → situationFamille
 *   regime_matrimonial           → regimeMatrimonial
 *   nom_marital                  → nomMarital
 *   prenom_pere                  → prenomPere
 *   prenom_mere                  → prenomMere
 *   nom_jeune_fille              → nomJeuneFille
 *   nombre_enfants_charge        → nombreEnfantsCharge
 *   numero_immatriculation       → numeroImmatriculation
 *   numero_acte_naissance        → numeroActeNaissance
 *   pays_immatriculation         → paysImmatriculation
 *   date_EER                     → dateEer
 *   motif_EER                    → motifEer
 *   modalite_EER                 → modaliteEer
 *   pays_kyc                     → paysKyc
 *   capacite_juridique           → capaciteJuridique
 *   date_effet                   → dateEffet
 *   segment_securite             → segmentSecuriteFinanciere
 *   categori_socio_professionnelle → categorieSocioProfessionnelle
 *   secteur_activite_eco         → secteurActiviteEconomique
 *   activite_risque              → activiteRisque
 *   description_activite         → descriptionActivite
 *   date_creation_activite       → dateCreationActivite
 *   pays_activite                → paysActivite
 *   pourcentage_activite         → pourcentageActivite
 *   cumule_pourcentage_activite  → cumulePourcentageActivite
 *   commentaire_activite         → commentaireActivite
 *   nom_employeur                → nomEmployeur
 *   domiciliation_salaire        → domiciliationSalaire
 *   depuis_quand                 → depuisQuand
 *   code_postal                  → codePostal
 *   ville                        → ville
 *   adresse                      → adresse
 *   mobile                       → mobile
 *   email                        → email
 *   pays_adresse_fiscal          → paysAdresseFiscale
 *   statut_residence             → statutResidence
 *   date_entree_territoire       → dateEntreeTerritoire
 *   consentement_credit_bureau   → consentementCreditBureau
 *   commentaire_relation         → commentaireRelation
 *   accounts[]                   → comptes
 *   personnes[]                  → personnesEnCharge
 */
@Data
public class TiersDTO {

    private Long id;

    // ── Identification ────────────────────────────────────────────────────────
    private String typeTiers;
    private String categorieClient;
    private String matriculeAgent;
    private String civilite;
    private String sexe;
    private String nom;
    private String prenom;
    private String nomAbrege;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String paysNaissance;
    private String paysNationalite;
    private String paysDoubleNationalite;
    private String numeroIdentifiantFiscal;
    private String numeroImmatriculation;
    private String numeroActeNaissance;
    private String paysImmatriculation;

    // ── Informations familiales ───────────────────────────────────────────────
    private String situationFamille;
    private String regimeMatrimonial;
    private String nomMarital;
    private String prenomPere;
    private String prenomMere;
    private String nomJeuneFille;
    private Integer nombreEnfantsCharge;

    // ── EER / KYC ────────────────────────────────────────────────────────────
    private LocalDate dateEer;
    private String motifEer;
    private String modaliteEer;
    private String paysKyc;

    // ── Situation juridique et financière ─────────────────────────────────────
    private String capaciteJuridique;
    private LocalDate dateEffet;
    private String segmentSecuriteFinanciere;
    private String categorieSocioProfessionnelle;
    private String secteurActiviteEconomique;
    private String activiteRisque;
    private String descriptionActivite;
    private LocalDate dateCreationActivite;
    private String paysActivite;
    private Integer pourcentageActivite;
    private Integer cumulePourcentageActivite;
    private String commentaireActivite;

    // ── Employeur ─────────────────────────────────────────────────────────────
    private String nomEmployeur;
    private Boolean domiciliationSalaire;
    private LocalDate depuisQuand;

    // ── Coordonnées et résidence ──────────────────────────────────────────────
    private String codePostal;
    private String ville;
    private String adresse;
    private String mobile;
    private String email;
    private String paysAdresseFiscale;
    private String statutResidence;
    private LocalDate dateEntreeTerritoire;
    private LocalDate dateSortieTerritoire;

    // ── Consentement & relation ───────────────────────────────────────────────
    private String consentementCreditBureau;
    private String commentaireRelation;

    // ── Comptes bancaires (BankAccountForms) ──────────────────────────────────
    private List<CompteBancaireDTO> comptes;

    // ── Personnes en charge (BankAccountForms - section personnes) ────────────
    private List<PersonneEnChargeDTO> personnesEnCharge;
}