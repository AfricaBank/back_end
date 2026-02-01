package org.africa.bank.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.africa.bank.constants.EtapeProcessus;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_etape")
@Data
public class HistoriqueEtape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EtapeProcessus etape;

    private LocalDateTime datePassage;
    private String utilisateur;
    private String commentaire;
    private String actionEffectuee; // Ajout du champ

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private DossierEER dossierEER; // Renommage de 'dossier' en 'dossierEER'
}