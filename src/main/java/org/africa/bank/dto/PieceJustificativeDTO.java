package org.africa.bank.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PieceJustificativeDTO {
    private Long id;
    private String docubaseId;
    private String nomDocument;
    private LocalDate dateCreationDocubase;
    private String typePJ;
    private String libelle;
    private Boolean obligatoire;
    private Boolean attache;
    private String tiersRole;
    private Long tiersId;
    private Long dossierEERId;
}