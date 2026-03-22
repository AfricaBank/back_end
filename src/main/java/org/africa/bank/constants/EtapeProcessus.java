package org.africa.bank.constants;

/**
 * Étapes du workflow EER selon les spécifications fonctionnelles.
 */
public enum EtapeProcessus {
    INITIATION,
    RECHERCHE_PERSONNE,
    CREATION_TIERS,
    AJOUT_TITULAIRE,
    AJOUT_PERSONNES_LIEES,
    ATTACHEMENT_PJ,         // Pièces justificatives
    CR_CONSEILLER,          // Compte rendu conseiller
    SOUMISSION_VALIDATION,
    EDITION_DOCUMENTS,
    ATTENTE_SIGNATURE,      // CA10 + convention signés
    TERMINE
}