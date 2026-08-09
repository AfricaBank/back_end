package org.africa.bank.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "avis_decision")
@Data
@NoArgsConstructor
public class AvisDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** VALIDE | A_REGULARISER | REJETE */
    @Column(nullable = false, length = 30)
    private String decision;

    /** Optionnel — affiché uniquement si VALIDE */
    @Column(columnDefinition = "TEXT")
    private String commentaire;

    /** Obligatoire si A_REGULARISER */
    @Column(columnDefinition = "TEXT")
    private String motifRenvoi;

    /** Obligatoire si REJETE */
    @Column(columnDefinition = "TEXT")
    private String motifRejet;

    private LocalDateTime dateDecision;

    /** Login du validateur */
    @Column(length = 100)
    private String validateur;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id", unique = true)
    private DossierEER dossierEER;
}