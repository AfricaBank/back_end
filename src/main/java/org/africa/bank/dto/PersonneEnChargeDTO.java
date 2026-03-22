package org.africa.bank.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
class PersonneEnChargeDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
}