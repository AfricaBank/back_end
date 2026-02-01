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
public class AjoutTitulaireState implements EtapeState {

    @Override
    public DossierEER initierDossier(Map<String, Object> params) {
        throw new IllegalStateException("L'initiation n'est pas disponible à l'étape AJOUT_TITULAIRE");
    }

    @Override
    public List<Tiers> rechercherTiers(Map<String, Object> criteres) {
        throw new IllegalStateException("La recherche n'est pas disponible à l'étape AJOUT_TITULAIRE");
    }

    @Override
    public DossierEER ajouterTitulaire(DossierEER dossier, Tiers tiers, boolean estCoTitulaire) {
        if (estCoTitulaire) {
            dossier.getCoTitulaires().add(tiers);
        } else {
            dossier.setTitulairePrincipal(tiers);
        }

        // Historique
        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.AJOUT_TITULAIRE);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire(estCoTitulaire ? "Co-titulaire ajouté" : "Titulaire principal ajouté");
        historique.setActionEffectuee("AJOUT_TITULAIRE");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossier;
    }

    @Override
    public DossierEER ajouterPersonnePhysique(DossierEER dossier, PersonneLPhysique personne) {
        throw new IllegalStateException("L'ajout de personne physique n'est pas disponible à l'étape AJOUT_TITULAIRE");
    }

    @Override
    public DossierEER ajouterPersonneMorale(DossierEER dossier, PersonneLM personne) {
        throw new IllegalStateException("L'ajout de personne morale n'est pas disponible à l'étape AJOUT_TITULAIRE");
    }

    @Override
    public boolean estEtapeValide(DossierEER dossier) {
        // Il faut au moins un titulaire (principal ou co-titulaire) pour valider l'étape
        return dossier.getTitulairePrincipal() != null || !dossier.getCoTitulaires().isEmpty();
    }

    @Override
    public DossierEER passerEtapeSuivante(DossierEER dossier) {
        dossier.setEtapeActuelle(EtapeProcessus.AJOUT_PERSONNES_LIEES);
        dossier.setDateModification(LocalDateTime.now());

        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.AJOUT_PERSONNES_LIEES);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire("Passage à l'étape d'ajout de personnes liées");
        historique.setActionEffectuee("CHANGEMENT_ETAPE");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossier;
    }

    @Override
    public DossierEER validerDossier(DossierEER dossier) {
        throw new IllegalStateException("La validation n'est pas disponible à l'étape AJOUT_TITULAIRE");
    }
}