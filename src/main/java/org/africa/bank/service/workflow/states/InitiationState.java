package org.africa.bank.service.workflow.states;

import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.constants.StatutDossier;
import org.africa.bank.entity.*;
import org.africa.bank.service.workflow.EtapeState;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Scope("prototype")
public class InitiationState implements EtapeState {

    @Override
    public DossierEER initierDossier(Map<String, Object> params) {
        DossierEER dossier = new DossierEER();

        // Générer référence unique
        String reference = "EER-" +
                LocalDateTime.now().getYear() + "-" +
                String.format("%06d", new Random().nextInt(999999));

        dossier.setReferenceDossier(reference);
        dossier.setStatut(StatutDossier.EN_COURS);
        dossier.setEtapeActuelle(EtapeProcessus.INITIATION);
        dossier.setDateCreation(LocalDateTime.now());

        // Récupérer le créateur depuis les params
        String createur = (String) params.getOrDefault("createur", "SYSTEM");
        dossier.setCreateur(createur);

        // Créer historique
        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.INITIATION);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(createur);
        historique.setCommentaire("Dossier EER initié");
        historique.setActionEffectuee("INITIATION");
        historique.setDossierEER(dossier);

        dossier.getHistoriqueEtapes().add(historique);

        return dossier;
    }

    @Override
    public boolean estEtapeValide(DossierEER dossier) {
        return dossier.getReferenceDossier() != null &&
                dossier.getDateCreation() != null &&
                dossier.getCreateur() != null;
    }

    @Override
    public DossierEER passerEtapeSuivante(DossierEER dossier) {
        dossier.setEtapeActuelle(EtapeProcessus.RECHERCHE_PERSONNE);
        dossier.setDateModification(LocalDateTime.now());

        // Historique
        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.RECHERCHE_PERSONNE);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire("Passage à l'étape de recherche");
        historique.setActionEffectuee("CHANGEMENT_ETAPE");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossier;
    }

    // Méthodes non implémentées pour cette étape
    @Override
    public List<Tiers> rechercherTiers(Map<String, Object> criteres) {
        throw new IllegalStateException("Recherche non disponible à l'étape INITIATION");
    }

    @Override
    public DossierEER ajouterTitulaire(DossierEER dossier, Tiers tiers, boolean estCoTitulaire) {
        throw new IllegalStateException("Ajout de titulaire non disponible à l'étape INITIATION");
    }

    @Override
    public DossierEER ajouterPersonnePhysique(DossierEER dossier, PersonneLPhysique personne) {
        throw new IllegalStateException("Ajout de personne physique non disponible à l'étape INITIATION");
    }

    @Override
    public DossierEER ajouterPersonneMorale(DossierEER dossier, PersonneLM personne) {
        throw new IllegalStateException("Ajout de personne morale non disponible à l'étape INITIATION");
    }

    @Override
    public DossierEER validerDossier(DossierEER dossier) {
        throw new IllegalStateException("Validation non disponible à l'étape INITIATION");
    }
}