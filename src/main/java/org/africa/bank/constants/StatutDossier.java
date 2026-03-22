package org.africa.bank.constants;

/**
 * Statuts du dossier EER selon les spécifications fonctionnelles.
 * Inclut les statuts de régularisation.
 */
public enum StatutDossier {
    EN_COURS,
    COMPLET,
    ANNULE,
    VALIDE,

    // Statuts de régularisation (retour vers le commercial)
    A_COMPLETER_CONFORMITE,   // Retour de la conformité locale
    A_COMPLETER_METIER,       // Retour du Responsable Retail
    A_COMPLETER_DG,           // Retour du DG
    A_REGULARISER_METIER,     // Retour du DIA
    A_REGULARISER_BO_CN1,     // Retour du BO CN1
    A_REGULARISER_SIGNATURE,  // Retour du BO N2 pour signature
    A_ABANDONNER_RESAISIE     // BOCN1 : processus erroné → abandon obligatoire
}