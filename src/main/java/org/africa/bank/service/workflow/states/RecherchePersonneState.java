package org.africa.bank.service.workflow.states;

import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.entity.*;
import org.africa.bank.service.TiersService;
import org.africa.bank.service.workflow.EtapeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class RecherchePersonneState implements EtapeState {

    @Autowired
    private TiersService tiersService;

    @Override
    public DossierEER initierDossier(Map<String, Object> params) {
        throw new IllegalStateException("L'initiation n'est pas disponible à l'étape RECHERCHE_PERSONNE");
    }

    @Override
    public List<Tiers> rechercherTiers(Map<String, Object> criteres) {
        return tiersService.searchEntities(criteres);
    }

    @Override
    public DossierEER ajouterTitulaire(DossierEER dossier, Tiers tiers, boolean estCoTitulaire) {
        throw new IllegalStateException("L'ajout de titulaire n'est pas disponible à l'étape RECHERCHE_PERSONNE");
    }

    @Override
    public DossierEER ajouterPersonnePhysique(DossierEER dossier, PersonneLPhysique personne) {
        throw new IllegalStateException("L'ajout de personne physique n'est pas disponible à l'étape RECHERCHE_PERSONNE");
    }

    @Override
    public DossierEER ajouterPersonneMorale(DossierEER dossier, PersonneLM personne) {
        throw new IllegalStateException("L'ajout de personne morale n'est pas disponible à l'étape RECHERCHE_PERSONNE");
    }

    @Override
    public boolean estEtapeValide(DossierEER dossier) {
        // La recherche est optionnelle, on peut passer à l'étape suivante même sans résultat
        return true;
    }

    @Override
    public DossierEER passerEtapeSuivante(DossierEER dossier) {
        dossier.setEtapeActuelle(EtapeProcessus.AJOUT_TITULAIRE);
        dossier.setDateModification(LocalDateTime.now());

        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.AJOUT_TITULAIRE);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire("Passage à l'étape d'ajout de titulaire");
        historique.setActionEffectuee("CHANGEMENT_ETAPE");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossier;
    }

    @Override
    public DossierEER validerDossier(DossierEER dossier) {
        throw new IllegalStateException("La validation n'est pas disponible à l'étape RECHERCHE_PERSONNE");
    }
}