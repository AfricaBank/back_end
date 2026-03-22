package org.africa.bank.dto;

import lombok.Data;

@Data
class CompteBancaireDTO {
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
