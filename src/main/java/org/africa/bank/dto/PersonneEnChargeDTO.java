package org.africa.bank.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO Personne en charge — aligné sur BankAccountForms.tsx (section personnes).
 *
 * personnes[i].nom              → nom
 * personnes[i].prenom           → prenom
 * personnes[i].date_naissance   → dateNaissance
 * personnes[i].sexe             → sexe
 */
@Data
public class PersonneEnChargeDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
}