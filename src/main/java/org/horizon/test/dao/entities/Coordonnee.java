package org.horizon.test.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "coordonnees")
public class Coordonnee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoordonnee;

    @NotNull
    @Digits( integer = 20, fraction = 0)
    private String telFixe;

    @Size(max = 20)
    @Email
    private String email;

    @NotNull
    @Size(max = 35)
    private String localite;

    @NotNull
    private String paysAdresseFiscale;

    @OneToOne
    @JoinColumn(name = "idDossier")
    @JsonIgnore
    private Dossier dossier;
}
