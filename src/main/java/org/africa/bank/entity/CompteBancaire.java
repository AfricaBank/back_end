package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Compte bancaire lié à un Tiers.
 * Aligné sur BankAccountForms.tsx :
 * type_compte, devise, motif_ouverture, racine, cle, cle_rib,
 * convention_compte, carton_signature
 */
@Entity
@Table(name = "comptes_bancaires")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CompteBancaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String typeCompte;

    @Column(length = 10)
    private String devise;

    @Column(length = 255)
    private String motifOuverture;

    @Column(length = 20)
    private String racine;

    @Column(length = 10)
    private String cle;

    @Column(length = 10)
    private String cleRib;

    @Column(length = 100)
    private String conventionCompte;

    @Column(length = 100)
    private String cartonSignature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tiers_id")
    private Tiers tiers;
}