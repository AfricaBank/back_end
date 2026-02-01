package org.africa.bank.entity;

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

    // Relation avec le titulaire principal (Tiers)
    @ManyToOne
    @JoinColumn(name = "tiers_id")
    private Tiers titulairePrincipal;

    // Relations avec les co-titulaires
    @ManyToMany
    @JoinTable(
            name = "dossier_co_titulaires",
            joinColumns = @JoinColumn(name = "dossier_id"),
            inverseJoinColumns = @JoinColumn(name = "tiers_id")
    )
    private List<Tiers> coTitulaires = new ArrayList<>();

    // Personnes physiques liées
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<PersonneLPhysique> personnesPhysiques = new ArrayList<>();

    // Personnes morales liées
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<PersonneLM> personnesMorales = new ArrayList<>();

    // Historique des étapes
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<HistoriqueEtape> historiqueEtapes = new ArrayList<>();

    // Dates
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private LocalDateTime dateTerminaison;

    // Métadonnées
    private String createur;
    private String commentaireGeneral;

    // Méthode utilitaire
    public boolean aUnTitulaire() {
        return titulairePrincipal != null || !coTitulaires.isEmpty();
    }
}