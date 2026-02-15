package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Ajouté
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
// Evite l'erreur ByteBuddyInterceptor lors de la sérialisation JSON
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

    // Relation avec le titulaire principal
    @ManyToOne(fetch = FetchType.EAGER)
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
    @JsonManagedReference // Gère le côté "Parent" de la relation
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<PersonneLPhysique> personnesPhysiques = new ArrayList<>();

    // Personnes morales liées
    @JsonManagedReference // Ajouté pour éviter la boucle infinie si PersonneLM a un champ dossierEER
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<PersonneLM> personnesMorales = new ArrayList<>();

    // Historique des étapes
    @JsonManagedReference // Ajouté
    @OneToMany(mappedBy = "dossierEER", cascade = CascadeType.ALL)
    private List<HistoriqueEtape> historiqueEtapes = new ArrayList<>();

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