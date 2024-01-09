package org.horizon.test.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name= "PersonneLPhysique")

public class PersonneLPhysique implements Serializable {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id")
	 private Long id;
	
	@Column(name = "typePersonne")
	private String typePersonne;
	
	// Rubrique Etat civil
	private String civilite;
    private String sexe;
    private String Nomfamille;
    @Column(length=50)
    private String Prenom;
    @Column(length=50)
    private String NomAbrege;
    @Column(length=20)
    private String NationalityPays;
    private String DoubleNationalityPays;
    private Boolean ResidentUs;
    private String TypeDocumentIdentification;
    private String NumeroDocument;
    private String PaysEmissiondocument;
    private String NIN;
    
    // Rubrique EER
    private String CategorieClientele;
    private Date DateEER;
    private String Modalite;
    private Date MotifEER;
    
    // Rubrique Naissance
    private Date DatedeNaissance;
    private String LieuNaissance;
    private String PaysNaissance;
    private String NomMere; 
    private String PrenomPere;
    private String PrenomMere;
    private Boolean NaissancePresumee;
    
    // Rubrique Situation de famille
    private String SituationFamiliale;
    private String RegimeMatrimoniale;
    private String NomMarital;
    
   // Rubrique Coordonnées
    private int NumTelephone;
    
    // Rubrique Adresse fiscale
    private String AdresseFiscale;
    private int CodePostal;
    private int localite;
    private String pays;
    private Boolean StatutRésidence;
    private String wilaya;
    private String commune;
    
    // Rubrique Activité professionnelle
    private String CategorieClienteleM;
    private String CategorieSocioProfessionnelle;
    private Boolean ConnaissanceInterne;
    private Boolean DirigeantBE;
    private Boolean BESociete;
    private Boolean DetentionCompteTitre;
    private Boolean DelegationKYC;
    private Boolean PresenceFlux;
    private String SecteurActiviteEconomique;
    private String LibelleAPE;
    private Date DateCreationActivite;
    private String PrincipalPaysActivite;
    private String ActiviteRisque;
    private String IndicateurProfessionnel;
    private int CodeSectoriel;
    private int AvoirsControles;
    private String libelle;
    private int pourcentage;
    private int PourcentageDetection;
    
    // Rubrique Conformité
    private String DetectionPPE;
    private String commentaire;
    private String TypePPE;
    private Boolean PPELocale;
    private Date DateIdentification;
    private Date DateInterrogationVigilance;
    private Boolean SousSanction;
    
    // Rubrique Relation bancaire à ouvrir
    private String IDNational;
    private int CodeSiege;
    private int racine;
    private String SegmentClientele;
    
    // Rubrique Enfants et personnes à charge
    private String EnfantEnCharge;

}
