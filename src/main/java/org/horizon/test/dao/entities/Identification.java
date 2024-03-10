package org.horizon.test.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "identifications")
public class Identification {

    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIdentificatiion;

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
    private String localite;

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

    @OneToOne
    @JoinColumn(name = "idDossier")
    @JsonIgnore
    private Dossier dossier;

}
