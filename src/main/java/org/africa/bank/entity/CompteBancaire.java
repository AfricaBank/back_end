package org.africa.bank.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "compte_bancaire")
@Data
public class CompteBancaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeCompte;
    private String devise;
    private String motifOuverture;

    private String racine;
    private String cle;
    private String cleRib;

    private String conventionCompte;
    private String cartonSignature;

    @ManyToOne
    @JoinColumn(name = "tiers_id")
    private Tiers tiers;
}

