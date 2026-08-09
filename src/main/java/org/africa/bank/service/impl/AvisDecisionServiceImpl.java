package org.africa.bank.service.impl;

import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.constants.StatutDossier;
import org.africa.bank.dto.AvisDecisionDTO;
import org.africa.bank.entity.AvisDecision;
import org.africa.bank.entity.DossierEER;
import org.africa.bank.exception.ResourceNotFoundException;
import org.africa.bank.exception.WorkflowException;
import org.africa.bank.repository.AvisDecisionRepository;
import org.africa.bank.repository.DossierEERRepository;
import org.africa.bank.service.AvisDecisionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AvisDecisionServiceImpl implements AvisDecisionService {

    private final AvisDecisionRepository avisRepository;
    private final DossierEERRepository dossierRepository;

    public AvisDecisionServiceImpl(
            AvisDecisionRepository avisRepository,
            DossierEERRepository dossierRepository) {
        this.avisRepository   = avisRepository;
        this.dossierRepository = dossierRepository;
    }

    @Override
    public AvisDecisionDTO soumettreAvis(Long dossierId, AvisDecisionDTO dto) {
        DossierEER dossier = dossierRepository.findById(dossierId)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier EER", dossierId));

        // Vérifier que le dossier est bien en attente de validation
        if (dossier.getEtapeActuelle() != EtapeProcessus.SOUMISSION_VALIDATION) {
            throw new WorkflowException(
                    "Ce dossier n'est pas en attente de validation. Étape actuelle : "
                            + dossier.getEtapeActuelle());
        }

        // Validation des champs selon la décision
        switch (dto.getDecision()) {
            case "VALIDE" -> {
                dossier.setStatut(StatutDossier.VALIDE);
                dossier.setEtapeActuelle(EtapeProcessus.TERMINE);
            }
            case "A_REGULARISER" -> {
                if (dto.getMotifRenvoi() == null || dto.getMotifRenvoi().isBlank()) {
                    throw new WorkflowException(
                            "Le motif de renvoi est obligatoire pour une décision A_REGULARISER.");
                }
                dossier.setStatut(StatutDossier.A_REGULARISER_METIER);
                // Retour à l'étape ajout des personnes liées pour que
                // l'initiateur puisse compléter données + documents + personnes
                dossier.setEtapeActuelle(EtapeProcessus.AJOUT_PERSONNES_LIEES);
            }
            case "REJETE" -> {
                if (dto.getMotifRejet() == null || dto.getMotifRejet().isBlank()) {
                    throw new WorkflowException(
                            "Le motif du rejet est obligatoire pour une décision REJETE.");
                }
                dossier.setStatut(StatutDossier.REJETE);
                // On garde l'étape SOUMISSION_VALIDATION — le dossier est
                // consultable mais bloqué (comme ANNULE, reprise possible)
                dossier.setEtapeActuelle(EtapeProcessus.SOUMISSION_VALIDATION);
            }
            default -> throw new WorkflowException(
                    "Décision invalide : " + dto.getDecision()
                            + ". Valeurs acceptées : VALIDE, A_REGULARISER, REJETE");
        }

        dossier.setDateModification(LocalDateTime.now());
        dossierRepository.save(dossier);

        // Sauvegarder ou mettre à jour l'avis
        AvisDecision avis = avisRepository
                .findByDossierEERId(dossierId)
                .orElse(new AvisDecision());

        avis.setDecision(dto.getDecision());
        avis.setCommentaire(dto.getCommentaire());
        avis.setMotifRenvoi(dto.getMotifRenvoi());
        avis.setMotifRejet(dto.getMotifRejet());
        avis.setDateDecision(LocalDateTime.now());
        avis.setValidateur(dto.getValidateur());
        avis.setDossierEER(dossier);

        return toDTO(avisRepository.save(avis));
    }

    @Override
    public AvisDecisionDTO getAvisParDossier(Long dossierId) {
        return avisRepository.findByDossierEERId(dossierId)
                .map(this::toDTO)
                .orElse(null);
    }

    private AvisDecisionDTO toDTO(AvisDecision avis) {
        AvisDecisionDTO dto = new AvisDecisionDTO();
        dto.setId(avis.getId());
        dto.setDecision(avis.getDecision());
        dto.setCommentaire(avis.getCommentaire());
        dto.setMotifRenvoi(avis.getMotifRenvoi());
        dto.setMotifRejet(avis.getMotifRejet());
        dto.setDateDecision(avis.getDateDecision());
        dto.setValidateur(avis.getValidateur());
        dto.setDossierEERId(
                avis.getDossierEER() != null ? avis.getDossierEER().getId() : null);
        return dto;
    }
}