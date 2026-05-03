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

    /**
     * Titulaire principal — EAGER + JsonIgnoreProperties pour éviter
     * la sérialisation des listes lazy de Tiers (accounts, personnes).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tiers_id")
    @JsonIgnoreProperties({"accounts", "personnes", "hibernateLazyInitializer", "handler"})
    private Tiers titulairePrincipal;

    /**
     * Co-titulaires — même protection.
     */
    @ManyToMany
    @JoinTable(
            name = "dossier_co_titulaires",
            joinColumns = @JoinColumn(name = "dossier_id"),
            inverseJoinColumns = @JoinColumn(name = "tiers_id")
    )
    @JsonIgnoreProperties({"accounts", "personnes", "hibernateLazyInitializer", "handler"})
    private List<Tiers> coTitulaires = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<PersonneLPhysique> personnesPhysiques = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<PersonneLM> personnesMorales = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<HistoriqueEtape> historiqueEtapes = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<PieceJustificative> piecesJustificatives = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private CRConseiller crConseiller;

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