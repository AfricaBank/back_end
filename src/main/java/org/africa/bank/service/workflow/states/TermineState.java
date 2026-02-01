package org.africa.bank.service.workflow.states;

import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.constants.StatutDossier;
import org.africa.bank.entity.*;
import org.africa.bank.service.workflow.EtapeState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class TermineState implements EtapeState {

    @Override
    public DossierEER initierDossier(Map<String, Object> params) {
        throw new IllegalStateException("L'initiation n'est pas disponible à l'étape TERMINE");
    }

    @Override
    public List<Tiers> rechercherTiers(Map<String, Object> criteres) {
        throw new IllegalStateException("La recherche n'est pas disponible à l'étape TERMINE");
    }

    @Override
    public DossierEER ajouterTitulaire(DossierEER dossier, Tiers tiers, boolean estCoTitulaire) {
        throw new IllegalStateException("L'ajout de titulaire n'est pas disponible à l'étape TERMINE");
    }

    @Override
    public DossierEER ajouterPersonnePhysique(DossierEER dossier, PersonneLPhysique personne) {
        throw new IllegalStateException("L'ajout de personne physique n'est pas disponible à l'étape TERMINE");
    }

    @Override
    public DossierEER ajouterPersonneMorale(DossierEER dossier, PersonneLM personne) {
        throw new IllegalStateException("L'ajout de personne morale n'est pas disponible à l'étape TERMINE");
    }

    @Override
    public boolean estEtapeValide(DossierEER dossier) {
        // Vérifier que le dossier est complet
        return dossier.getTitulairePrincipal() != null || !dossier.getCoTitulaires().isEmpty();
    }

    @Override
    public DossierEER passerEtapeSuivante(DossierEER dossier) {
        throw new IllegalStateException("Le dossier est déjà terminé");
    }

    @Override
    public DossierEER validerDossier(DossierEER dossier) {
        dossier.setStatut(StatutDossier.VALIDE);
        dossier.setDateModification(LocalDateTime.now());

        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.TERMINE);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire("Dossier validé avec succès");
        historique.setActionEffectuee("VALIDATION_DOSSIER");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossier;
    }
}