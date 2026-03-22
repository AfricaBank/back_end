package org.africa.bank.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CRConseillerDTO {
    private Long id;
    private Boolean informationsDefavorablesTrouvees;
    private LocalDate dateRecherche;
    private String theme;
    private String detailInformationDefavorable;
    private String facteurAttenuation;
    private String commentaireInfoDefavorable;
    private Boolean nouveauNiveauRisque;
    private String risquePropose;
    private String commentaireRisque;
    private String natureObjetRelationAffaires;
    private String actifsConfies;
    private String utilisationEspeces;
    private String financements;
    private String autresInfoTransactionnelles;
    private String analyseCohérence;
    private List<EvaluationQualitativeDTO> evaluationsQualitatives;
    private String crEntretien;
    private Long dossierEERId;
}
