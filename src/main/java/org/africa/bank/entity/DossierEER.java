package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.constants.StatutDossier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dossier_eer")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DossierEER {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String referenceDossier;

    @Enumerated(EnumType.STRING)
    private StatutDossier statut;

    @Enumerated(EnumType.STRING)
    private EtapeProcessus etapeActuelle;

    // ── Titulaire principal ───────────────────────────────────────────────────
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tiers_id")
    @JsonIgnoreProperties({"accounts", "personnes", "hibernateLazyInitializer", "handler"})
    private Tiers titulairePrincipal;

    // ── Co-titulaires ─────────────────────────────────────────────────────────
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dossier_co_titulaires",
            joinColumns = @JoinColumn(name = "dossier_id"),
            inverseJoinColumns = @JoinColumn(name = "tiers_id")
    )
    @JsonIgnoreProperties({"accounts", "personnes", "hibernateLazyInitializer", "handler"})
    private List<Tiers> coTitulaires = new ArrayList<>();

    // ── Personnes liées physiques ─────────────────────────────────────────────
    // CORRECTION : EAGER pour éviter LazyInitializationException à la sérialisation
    @JsonManagedReference
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<PersonneLPhysique> personnesPhysiques = new ArrayList<>();

    // ── Personnes liées morales ───────────────────────────────────────────────
    @JsonManagedReference
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<PersonneLM> personnesMorales = new ArrayList<>();

    // ── Historique ────────────────────────────────────────────────────────────
    @JsonManagedReference
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<HistoriqueEtape> historiqueEtapes = new ArrayList<>();

    // ── Pièces justificatives ─────────────────────────────────────────────────
    @JsonManagedReference
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<PieceJustificative> piecesJustificatives = new ArrayList<>();

    // ── CR Conseiller ─────────────────────────────────────────────────────────
    @JsonManagedReference
    @OneToOne(mappedBy = "dossierEER", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private CRConseiller crConseiller;

    // ── Champs simples ────────────────────────────────────────────────────────
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private LocalDateTime dateTerminaison;

    private String createur;
    private String commentaireGeneral;
    private String typePersonne;
    private String codeSiege;
    private String natureRelation;
    private String codeExploitant;
    private String nomCollectivite;
    private String nomExploitant;
    private String civiliteCollectivite;

    public boolean aUnTitulaire() {
        return titulairePrincipal != null || !coTitulaires.isEmpty();
    }
}