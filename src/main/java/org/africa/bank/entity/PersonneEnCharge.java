package org.africa.bank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "personne_en_charge")
@Data
public class PersonneEnCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String sexe;

    @ManyToOne
    @JoinColumn(name = "tiers_id")
    private Tiers tiers;
}

