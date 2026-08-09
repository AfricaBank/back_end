package org.africa.bank.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AvisDecisionDTO {
    private Long id;
    private String decision;
    private String commentaire;
    private String motifRenvoi;
    private String motifRejet;
    private LocalDateTime dateDecision;
    private String validateur;
    private Long dossierEERId;
}