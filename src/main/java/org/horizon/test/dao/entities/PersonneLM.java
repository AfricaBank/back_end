package org.horizon.test.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @NotNull
    @Size(min = 5, max = 5)
    private String codeExploitant;

    //  Identification
    @NotNull
    @Size(max = 35)
    private String raisonSociale;

    @Size(max = 25)
    private String nomAbrege;

    @NotNull
    @Size(max = 35)
    private String enseigne;

    @NotNull
    private String formeJuridique;

    @NotNull
    private String paysImmatriculation;

    @NotNull
    @Size(max = 35)
    private String localite_id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate dateCreationActivite;

    @NotNull
    private Boolean detentionPartPorteurAnonyme;

    @NotNull
    private Boolean societeDomiciliation;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateInterrogationVigilance;

    @NotNull
    private Boolean sousSanction;


    //  Coordonnees
    @NotNull
    @Digits( integer = 20, fraction = 0)
    private String telFixe;

    @Size(max = 20)
    @Email
    private String email;

    @NotNull
    @Size(max = 50)
    private String adresseFiscale;

    @NotNull
    @Size(max = 35)
    private String localite_coor;

    @NotNull
    private String paysAdresseFiscale;


    //  EER
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @FutureOrPresent
    private LocalDate dateEER;

    @NotNull
    private String motifEER;

    @NotNull
    private String modaliteEER;


    //  Banque
    @NotNull
    private String idNational;

    @NotNull
    @Digits(fraction = 0, integer = 5)
    private Integer codeSiege;

    @NotNull
    private Long racine;    // value  = new Date().getTime();

    @NotNull
    private String codeSegmentClientele;
}