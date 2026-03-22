package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Compte rendu conseiller — onglet CR du processus EER.
 * Contient : infos défavorables, profil transactionnel, évaluation qualitative,
 * CR de l'entretien.
 */
@Entity
@Table(name = "cr_conseiller")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CRConseiller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Informations défavorables ─────────────────────────────────────────────
    /**
     * Affiché si avoirs > 5M€, client PPE ou personne liée PPE.
     */
    private Boolean informationsDefavorablesTrouvees;

    private LocalDate dateRecherche;

    @Column(length = 100)
    private String theme;

    @Column(length = 500)
    private String detailInformationDefavorable;

    @Column(length = 100)
    private String facteurAttenuation;

    @Column(length = 500)
    private String commentaireInfoDefavorable;

    // ── Niveau de risque ──────────────────────────────────────────────────────
    private Boolean nouveauNiveauRisque;

    @Column(length = 50)
    private String risquePropose;

    @Column(length = 500)
    private String commentaireRisque;

    // ── Profil transactionnel (clients HR avoirs < 5M€ ou avoirs >= 5M€) ─────
    @Column(length = 300)
    private String natureObjetRelationAffaires;

    @Column(length = 300)
    private String actifsConfies;

    @Column(length = 300)
    private String utilisationEspeces;

    @Column(length = 300)
    private String financements;

    @Column(length = 300)
    private String autresInfoTransactionnelles;

    @Column(length = 300)
    private String analyseCohérence;

    // ── Évaluation qualitative ────────────────────────────────────────────────
    @OneToMany(mappedBy = "crConseiller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationQualitative> evaluationsQualitatives = new ArrayList<>();

    // ── CR de l'entretien ─────────────────────────────────────────────────────
    @Column(length = 1500)
    private String crEntretien;

    // ── Relation dossier ──────────────────────────────────────────────────────
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id")
    private DossierEER dossierEER;
}