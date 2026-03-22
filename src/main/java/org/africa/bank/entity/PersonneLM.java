package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Personne liée morale (Garant PM).
 * Correction : @Digits retiré du champ String telFixe.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "personnes_morales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PersonneLM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPLM;

    @Column(length = 5)
    private String codeExploitant;

    // ── Identification ────────────────────────────────────────────────────────
    @Size(max = 35)
    private String raisonSociale;

    @Size(max = 25)
    private String nomAbrege;

    @Size(max = 35)
    private String enseigne;

    private String formeJuridique;
    private String paysImmatriculation;

    @Size(max = 35)
    private String localite_id;

    private LocalDate dateCreationActivite;
    private Boolean detentionPartPorteurAnonyme;
    private Boolean societeDomiciliation;
    private LocalDate dateInterrogationVigilance;
    private Boolean sousSanction;

    // ── Coordonnées ───────────────────────────────────────────────────────────
    @Column(length = 20)
    // CORRECTION : @Digits retiré — @Digits ne s'applique pas aux String
    private String telFixe;

    @Size(max = 20)
    @Email
    private String email;

    @Size(max = 50)
    private String adresseFiscale;

    @Size(max = 35)
    private String localite_coor;

    private String paysAdresseFiscale;

    // ── EER ───────────────────────────────────────────────────────────────────
    private LocalDate dateEER;
    private String motifEER;
    private String modaliteEER;

    // ── Relation bancaire ─────────────────────────────────────────────────────
    @Column(length = 13)
    private String idNational;

    @Column(length = 5)
    private Integer codeSiege;

    private Long racine;
    private String codeSegmentClientele;

    // ── Relations JPA ─────────────────────────────────────────────────────────
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id")
    private DossierEER dossierEER;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tiers_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Tiers tiers;
}