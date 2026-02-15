package org.africa.bank.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.africa.bank.constants.EtapeProcessus;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_etape")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HistoriqueEtape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EtapeProcessus etape;

    private LocalDateTime datePassage;
    private String utilisateur;
    private String commentaire;
    private String actionEffectuee;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id")
    private DossierEER dossierEER;
}