package org.africa.bank.service.workflow.states;

import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.entity.*;
import org.africa.bank.service.workflow.EtapeState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class AjoutPersonnesLieesState implements EtapeState {

    @Override
    public DossierEER initierDossier(Map<String, Object> params) {
        throw new IllegalStateException("L'initiation n'est pas disponible à l'étape AJOUT_PERSONNES_LIEES");
    }

    @Override
    public List<Tiers> rechercherTiers(Map<String, Object> criteres) {
        throw new IllegalStateException("La recherche n'est pas disponible à l'étape AJOUT_PERSONNES_LIEES");
    }

    @Override
    public DossierEER ajouterTitulaire(DossierEER dossier, Tiers tiers, boolean estCoTitulaire) {
        throw new IllegalStateException("L'ajout de titulaire n'est pas disponible à l'étape AJOUT_PERSONNES_LIEES");
    }

    @Override
    public DossierEER ajouterPersonnePhysique(DossierEER dossier, PersonneLPhysique personne) {
        dossier.getPersonnesPhysiques().add(personne);

        // Historique
        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.AJOUT_PERSONNES_LIEES);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire("Personne physique liée ajoutée");
        historique.setActionEffectuee("AJOUT_PERSONNE_PHYSIQUE");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossier;
    }

    @Override
    public DossierEER ajouterPersonneMorale(DossierEER dossier, PersonneLM personne) {
        dossier.getPersonnesMorales().add(personne);

        // Historique
        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.AJOUT_PERSONNES_LIEES);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire("Personne morale liée ajoutée");
        historique.setActionEffectuee("AJOUT_PERSONNE_MORALE");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossier;
    }

    @Override
    public boolean estEtapeValide(DossierEER dossier) {
        // L'ajout de personnes liées est optionnel, donc on peut toujours passer à l'étape suivante
        return true;
    }

    @Override
    public DossierEER passerEtapeSuivante(DossierEER dossier) {
        dossier.setEtapeActuelle(EtapeProcessus.TERMINE);
        dossier.setDateModification(LocalDateTime.now());

        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.TERMINE);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire("Passage à l'étape de terminaison");
        historique.setActionEffectuee("CHANGEMENT_ETAPE");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossier;
    }

    @Override
    public DossierEER validerDossier(DossierEER dossier) {
        throw new IllegalStateException("La validation n'est pas disponible à l'étape AJOUT_PERSONNES_LIEES");
    }
}