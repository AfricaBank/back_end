package org.africa.bank.service.impl;

import org.africa.bank.dto.CRConseillerDTO;
import org.africa.bank.dto.EvaluationQualitativeDTO;
import org.africa.bank.entity.CRConseiller;
import org.africa.bank.entity.DossierEER;
import org.africa.bank.entity.EvaluationQualitative;
import org.africa.bank.exception.ResourceNotFoundException;
import org.africa.bank.exception.WorkflowException;
import org.africa.bank.repository.CRConseillerRepository;
import org.africa.bank.repository.DossierEERRepository;
import org.africa.bank.service.CRConseillerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CRConseillerServiceImpl implements CRConseillerService {

    private final CRConseillerRepository crRepository;
    private final DossierEERRepository dossierRepository;
    private final ModelMapper modelMapper;

    public CRConseillerServiceImpl(
            CRConseillerRepository crRepository,
            DossierEERRepository dossierRepository,
            ModelMapper modelMapper) {
        this.crRepository = crRepository;
        this.dossierRepository = dossierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CRConseillerDTO sauvegarderCR(Long dossierId, CRConseillerDTO dto) {
        DossierEER dossier = dossierRepository.findById(dossierId)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier EER", dossierId));

        // Valider le CR avant sauvegarde
        validerCR(dto);

        // Créer ou mettre à jour le CR (un seul CR par dossier)
        CRConseiller cr = crRepository.findByDossierEERId(dossierId)
                .orElse(new CRConseiller());

        modelMapper.map(dto, cr);
        cr.setDossierEER(dossier);

        // Gérer les évaluations qualitatives manuellement
        // (ModelMapper ne gère pas bien les listes avec cascade)
        if (dto.getEvaluationsQualitatives() != null) {
            List<EvaluationQualitative> evaluations = new ArrayList<>();
            for (EvaluationQualitativeDTO evalDto : dto.getEvaluationsQualitatives()) {
                EvaluationQualitative eval = modelMapper.map(
                        evalDto, EvaluationQualitative.class);

                // Commentaire obligatoire si appréciation = Oui
                if (Boolean.TRUE.equals(eval.getAppreciation())
                        && (eval.getCommentaire() == null
                        || eval.getCommentaire().isBlank())) {
                    throw new WorkflowException(
                            "Le commentaire est obligatoire si l'appréciation est 'Oui' "
                                    + "pour la question : " + eval.getQuestion());
                }
                eval.setCrConseiller(cr);
                evaluations.add(eval);
            }
            cr.getEvaluationsQualitatives().clear();
            cr.getEvaluationsQualitatives().addAll(evaluations);
        }

        CRConseiller saved = crRepository.save(cr);
        return toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CRConseillerDTO getCRParDossier(Long dossierId) {
        return crRepository.findByDossierEERId(dossierId)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CR Conseiller pour le dossier " + dossierId));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean crEstRenseigne(Long dossierId) {
        return crRepository.findByDossierEERId(dossierId)
                .map(cr -> cr.getCrEntretien() != null
                        && !cr.getCrEntretien().isBlank())
                .orElse(false);
    }

    // ── Validation ────────────────────────────────────────────────────────────

    private void validerCR(CRConseillerDTO dto) {
        // CR de l'entretien obligatoire
        if (dto.getCrEntretien() == null || dto.getCrEntretien().isBlank()) {
            throw new WorkflowException(
                    "Le CR de l'entretien est obligatoire (max 1500 caractères).");
        }
        if (dto.getCrEntretien().length() > 1500) {
            throw new WorkflowException(
                    "Le CR de l'entretien ne peut pas dépasser 1500 caractères.");
        }
        // Si nouveau niveau de risque → risque proposé + commentaire obligatoires
        if (Boolean.TRUE.equals(dto.getNouveauNiveauRisque())) {
            if (dto.getRisquePropose() == null || dto.getRisquePropose().isBlank()) {
                throw new WorkflowException(
                        "Le risque proposé est obligatoire si un nouveau niveau de risque est indiqué.");
            }
            if (dto.getCommentaireRisque() == null || dto.getCommentaireRisque().isBlank()) {
                throw new WorkflowException(
                        "Le commentaire est obligatoire pour motiver le nouveau niveau de risque.");
            }
        }
    }

    private CRConseillerDTO toDTO(CRConseiller cr) {
        return modelMapper.map(cr, CRConseillerDTO.class);
    }
}
