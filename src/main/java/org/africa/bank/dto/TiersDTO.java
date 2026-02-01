package org.africa.bank.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class TiersDTO {
    private Long id;
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
    private String situationFamille;
    private String regimeMatrimonial;
    private String nomMarital;
    private String prenomPere;
    private String prenomMere;
    private String nomJeuneFille;
    private Integer nombreEnfantsCharge;
    private Date dateEer;
    private String motifEer;
    private String modaliteEer;
    private String paysKyc;
    private String capaciteJuridique;
    private Date dateEffet;
    private String segmentSecuriteFinanciere;
    private String categorieSocioProfessionnelle;
    private String secteurActiviteEconomique;
    private String activiteRisque;
    private String descriptionActivite;
    private Date dateCreationActivite;
    private String paysActivite;
    private Integer pourcentageActivite;
    private Integer cumulePourcentageActivite;
    private String commentaireActivite;
    private String nomEmployeur;
    private Boolean domiciliationSalaire;
    private Date depuisQuand;
    private String codePostal;
    private String ville;
    private String adresse;
    private String mobile;
    private String email;
    private String paysAdresseFiscale;
    private String statutResidence;
    private Date dateEntreeTerritoire;
    private String consentementCreditBureau;
    private String commentaireRelation;

    // Pour la recherche
    private List<CompteBancaireDTO> comptes;
    private List<PersonneEnChargeDTO> personnesEnCharge;
}

@Data
class CompteBancaireDTO {
    private Long id;
    private String typeCompte;
    private String devise;
    private String motifOuverture;
    private String racine;
    private String cle;
    private String cleRib;
    private String conventionCompte;
    private String cartonSignature;
}

@Data
class PersonneEnChargeDTO {
    private Long id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String sexe;
}