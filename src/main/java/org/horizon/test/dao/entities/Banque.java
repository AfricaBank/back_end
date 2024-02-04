package org.horizon.test.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "banques")
public class Banque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBanque;

    @NotNull
    private String idNational;

    @NotNull
    @Digits(fraction = 0, integer = 5)
    private Integer codeSiege;

    @NotNull
    private Long racine;    // value  = new Date().getTime();

    @NotNull
    private String codeSegmentClientele;

    @OneToOne
    @JoinColumn(name = "idDossier")
    @JsonIgnore
    private Dossier dossier;

}