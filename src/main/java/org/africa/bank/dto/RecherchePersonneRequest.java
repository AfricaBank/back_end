package org.africa.bank.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * Critères de recherche du tiers selon les specs :
 * - Par Nom (+ civilité, prénom, date naissance)
 * - Par Siège/Racine
 * - Par ID National
 * - Par ID Bénéficiaire Effectif
 */
@Data
public class RecherchePersonneRequest {

    // ── Critère : recherche par Nom ───────────────────────────────────────────
    private String civilite;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;

    // ── Critère : recherche par Siège/Racine ─────────────────────────────────
    private String siege;       // max 5 chiffres
    private String racine;      // max 6 chiffres

    // ── Critère : recherche par ID National ──────────────────────────────────
    private String idNational;  // max 13 caractères

    // ── Critère : recherche par Bénéficiaire Effectif ─────────────────────────
    private String nomBE;
    private String prenomBE;

    // ── Contexte dossier ─────────────────────────────────────────────────────
    private Long dossierId;     // pour lier le résultat au dossier en cours
}