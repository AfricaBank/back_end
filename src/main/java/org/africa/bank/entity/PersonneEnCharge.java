package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Personne en charge liée à un Tiers.
 * Aligné sur BankAccountForms.tsx (section personnes) :
 * nom, prenom, date_naissance, sexe
 */
@Entity
@Table(name = "personnes_en_charge")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PersonneEnCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nom;

    @Column(length = 50)
    private String prenom;

    private LocalDate dateNaissance;

    @Column(length = 10)
    private String sexe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tiers_id")
    private Tiers tiers;
}