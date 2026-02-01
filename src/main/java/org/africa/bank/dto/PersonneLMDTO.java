package org.africa.bank.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PersonneLMDTO {
    private Long idPLM;
    private String codeExploitant;
    private String raisonSociale;
    private String nomAbrege;
    private String enseigne;
    private String formeJuridique;
    private String paysImmatriculation;
    private String localiteId;
    private LocalDate dateCreationActivite;
    private Boolean detentionPartPorteurAnonyme;
    private Boolean societeDomiciliation;
    private LocalDate dateInterrogationVigilance;
    private Boolean sousSanction;
    private String telFixe;
    private String email;
    private String adresseFiscale;
    private String localiteCoor;
    private String paysAdresseFiscale;
    private LocalDate dateEER;
    private String motifEER;
    private String modaliteEER;
    private String idNational;
    private Integer codeSiege;
    private Long racine;
    private String codeSegmentClientele;

    // Références
    private Long dossierEERId;
    private Long tiersReferenceId;
    private String typeRelationMorale;
}