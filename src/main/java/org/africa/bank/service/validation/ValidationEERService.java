package org.africa.bank.service.validation;

import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.entity.DossierEER;
import org.africa.bank.entity.Tiers;
import org.springframework.stereotype.Service;

@Service
public class ValidationEERService {

    public void validerAjoutTitulaire(DossierEER dossier, Tiers nouveauTitulaire, boolean estCoTitulaire) {

        // Règle 1: Un dossier ne peut avoir qu'un seul titulaire principal
        if (!estCoTitulaire && dossier.getTitulairePrincipal() != null) {
            throw new ValidationException("Un titulaire principal existe déjà");
        }

        // Règle 2: Un Tiers ne peut pas être à la fois titulaire et co-titulaire
        if (estCoTitulaire && dossier.getTitulairePrincipal() != null &&
                dossier.getTitulairePrincipal().getId().equals(nouveauTitulaire.getId())) {
            throw new ValidationException("Un Tiers ne peut pas être à la fois titulaire et co-titulaire");
        }

        // Règle 3: Vérifier les doublons dans les co-titulaires
        if (estCoTitulaire) {
            boolean dejaCoTitulaire = dossier.getCoTitulaires().stream()
                    .anyMatch(t -> t.getId().equals(nouveauTitulaire.getId()));
            if (dejaCoTitulaire) {
                throw new ValidationException("Ce Tiers est déjà co-titulaire");
            }
        }
    }

    public void validerDossierComplet(DossierEER dossier) {
        // Règle 1: Doit avoir au moins un titulaire
        if (!dossier.aUnTitulaire()) {
            throw new ValidationException("Le dossier doit avoir au moins un titulaire");
        }

        // Règle 2: Toutes les étapes doivent être complètes
        if (dossier.getEtapeActuelle() != EtapeProcessus.TERMINE) {
            throw new ValidationException("Le processus n'est pas terminé");
        }

        // Règle 3: Vérifier la cohérence des données
        validerCohérenceDonnées(dossier);
    }

    private void validerCohérenceDonnées(DossierEER dossier) {
        // Vérifications spécifiques selon votre métier
        // Ex: dates cohérentes, champs obligatoires remplis, etc.
        // Cette méthode peut être étendue selon les besoins
    }
}

class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}