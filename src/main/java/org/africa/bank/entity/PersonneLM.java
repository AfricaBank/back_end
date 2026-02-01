package org.africa.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "personnes_morales")
public class PersonneLM {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPLM;

    //  Dossier
    @Size(min = 5, max = 5)
    private String codeExploitant;

    //  Identification
    @Size(max = 35)
    private String raisonSociale;

    @Size(max = 25)
    private String nomAbrege;

    @Size(max = 35)
    private String enseigne;

    private String formeJuridique;

    private String paysImmatriculation;

    @Size(max = 35)
    private String localite_id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate dateCreationActivite;

    private Boolean detentionPartPorteurAnonyme;

    private Boolean societeDomiciliation;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateInterrogationVigilance;

    private Boolean sousSanction;


    //  Coordonnees
    @Digits( integer = 20, fraction = 0)
    private String telFixe;

    @Size(max = 20)
    @Email
    private String email;

    @Size(max = 50)
    private String adresseFiscale;

    @Size(max = 35)
    private String localite_coor;

    private String paysAdresseFiscale;


    //  EER
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @FutureOrPresent
    private LocalDate dateEER;

    private String motifEER;

    private String modaliteEER;


    //  Banque
    private String idNational;

    @Digits(fraction = 0, integer = 5)
    private Integer codeSiege;

    private Long racine;    // value  = new Date().getTime();


    private String codeSegmentClientele;


    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private DossierEER dossierEER;  // or dossier, dossierEer, etc.

    @ManyToOne
    @JoinColumn(name = "tiers_id")
    private Tiers tiers; // Si la personne morale est liée à un Tiers spécifique
}
