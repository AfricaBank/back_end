package org.africa.bank.dto;

import lombok.Data;

@Data
public class EvaluationQualitativeDTO {
    private Long id;
    private String question;
    private Boolean appreciation;
    private String commentaire;
}
