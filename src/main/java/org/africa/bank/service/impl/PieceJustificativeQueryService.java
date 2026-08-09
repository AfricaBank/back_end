package org.africa.bank.service.impl;

import org.africa.bank.dto.PieceJustificativeDTO;
import org.africa.bank.entity.PieceJustificative;
import org.africa.bank.repository.PieceJustificativeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PieceJustificativeQueryService {

    private final PieceJustificativeRepository pjRepository;

    public PieceJustificativeQueryService(
            PieceJustificativeRepository pjRepository) {
        this.pjRepository = pjRepository;
    }

    public List<PieceJustificativeDTO> getPJParDossier(Long dossierId) {
        return pjRepository.findByDossierEERId(dossierId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PieceJustificativeDTO> getPJObligatoiresNonAttachees(Long dossierId) {
        return pjRepository.findPJObligatoiresNonAttachees(dossierId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public boolean toutesLesPJObligatoiresSontAttachees(Long dossierId) {
        return pjRepository.toutesLesPJObligatoiresSontAttachees(dossierId);
    }

    // Mapping explicite — ModelMapper rate tiersId car c'est un Long simple
    // sans relation JPA, et rate dossierEERId à cause du LAZY loading
    private PieceJustificativeDTO toDTO(PieceJustificative pj) {
        PieceJustificativeDTO dto = new PieceJustificativeDTO();
        dto.setId(pj.getId());
        dto.setDocubaseId(pj.getDocubaseId());
        dto.setNomDocument(pj.getNomDocument());
        dto.setDateCreationDocubase(pj.getDateCreationDocubase());
        dto.setTypePJ(pj.getTypePJ());
        dto.setLibelle(pj.getLibelle());
        dto.setObligatoire(pj.getObligatoire());
        dto.setAttache(pj.getAttache());
        dto.setTiersRole(pj.getTiersRole());
        dto.setTiersId(pj.getTiersId());   // ← champ critique
        dto.setDossierEERId(
                pj.getDossierEER() != null ? pj.getDossierEER().getId() : null
        );
        return dto;
    }
}