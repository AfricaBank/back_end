package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ligne du tableau d'évaluation qualitative.
 * Une question = une appréciation (Oui/Non) + un commentaire obligatoire si Oui.
 */
@Entity
@Table(name = "evaluations_qualitatives")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EvaluationQualitative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String question;

    /** true = Oui, false = Non */
    private Boolean appreciation;

    /** Obligatoire si appreciation = true */
    @Column(length = 500)
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cr_conseiller_id")
    private CRConseiller crConseiller;
}