package org.africa.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * Résultat de la recherche — deux blocs selon les specs :
 * 1. Dossiers en cours dans MINSA
 * 2. Tiers déjà créés dans ATLAS
 */
@Data
@AllArgsConstructor
public class RecherchePersonneResponse {

    /** Dossiers en cours de constitution dans l'outil (lecture seule) */
    private List<TiersDTO> dossiersEnCours;

    /** Tiers existants dans ATLAS (modifiables si même code exploitant) */
    private List<TiersDTO> tiersExistants;

    /** Indique si une création est possible même si des résultats existent */
    private boolean creationPossible;
}