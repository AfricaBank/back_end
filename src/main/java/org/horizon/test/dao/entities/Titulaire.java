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
@Table(name = "titulaires")
public class Titulaire {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTitulaire;
    private String civilite;
    @NotBlank
    @Size(min = 5, max = 50)
    private String nom;

    @NotBlank
    @Size(min = 5, max = 50)
    private String prenom;

    @NotNull
    @Temporal(TemporalType.DATE)
    @PastOrPresent
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateNaissance;

    @Digits(fraction = 0, integer = 7)
    @Size(max = 5)
    private Integer siege;

    @Digits(fraction = 0, integer = 7)
    @Size(max = 5)
    private Integer racine;

    @Size(max = 13)
    private String idNational;

    @Size(min = 5, max = 50)
    private String nomBeneficiaire;

    @Size(min = 5, max = 50)
    private String prenomBeneficiaire;

}