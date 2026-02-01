package org.africa.bank.service.workflow;

import org.africa.bank.entity.*;
import java.util.Map;
import java.util.List;

public interface EtapeState {

    // Initialiser un dossier
    DossierEER initierDossier(Map<String, Object> params);

    // Rechercher des Tiers existants
    List<Tiers> rechercherTiers(Map<String, Object> criteres);

    // Ajouter un titulaire (principal ou co-titulaire)
    DossierEER ajouterTitulaire(DossierEER dossier, Tiers tiers, boolean estCoTitulaire);

    // Ajouter une personne physique liée
    DossierEER ajouterPersonnePhysique(DossierEER dossier, PersonneLPhysique personne);

    // Ajouter une personne morale liée
    DossierEER ajouterPersonneMorale(DossierEER dossier, PersonneLM personne);

    // Valider si l'étape actuelle est complète
    boolean estEtapeValide(DossierEER dossier);

    // Passer à l'étape suivante
    DossierEER passerEtapeSuivante(DossierEER dossier);

    // Valider le dossier complet
    DossierEER validerDossier(DossierEER dossier);
}