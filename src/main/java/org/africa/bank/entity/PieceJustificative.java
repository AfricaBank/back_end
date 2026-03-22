package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Pièce justificative attachée à un tiers dans un dossier EER.
 * Les documents sont stockés dans Docubase — MINSA ne stocke que les métadonnées.
 */
@Entity
@Table(name = "pieces_justificatives")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PieceJustificative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Référence Docubase ────────────────────────────────────────────────────
    /** Identifiant du document dans Docubase */
    @Column(length = 100)
    private String docubaseId;

    /** Nom du document tel qu'indexé dans Docubase */
    @Column(length = 200)
    private String nomDocument;

    /** Date de création dans Docubase */
    private LocalDate dateCreationDocubase;

    // ── Métadonnées PJ ────────────────────────────────────────────────────────
    /** Type de pièce (ex: CNI, Passeport, Justificatif domicile...) */
    @Column(length = 100)
    private String typePJ;

    /** Libellé affiché dans la liste des PJ */
    @Column(length = 200)
    private String libelle;

    /** true = obligatoire, false = facultatif */
    private Boolean obligatoire;

    /** true = document attaché et validé */
    private Boolean attache;

    /** À quel tiers cette PJ est rattachée (titulaire, co-titulaire, personne liée) */
    @Column(length = 50)
    private String tiersRole;   // TITULAIRE | CO_TITULAIRE | MANDATAIRE | GARANT_PP | etc.

    private Long tiersId;       // ID du tiers concerné

    // ── Relation dossier ─────────────────────────────────────────────────────
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id")
    private DossierEER dossierEER;
}