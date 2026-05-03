package org.africa.bank.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO Compte bancaire — aligné sur BankAccountForms.tsx.
 *
 * accounts[i].type_compte         → typeCompte
 * accounts[i].devise              → devise
 * accounts[i].motif_ouverture     → motifOuverture
 * accounts[i].racine              → racine
 * accounts[i].cle                 → cle
 * accounts[i].cle_rib             → cleRib
 * accounts[i].convention_compte   → conventionCompte
 * accounts[i].carton_signature    → cartonSignature
 */
@Data
public class CompteBancaireDTO {
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