package org.africa.bank.service.impl;

import org.africa.bank.dto.PieceJustificativeDTO;
import org.africa.bank.entity.DossierEER;
import org.africa.bank.entity.PieceJustificative;
import org.africa.bank.exception.ResourceNotFoundException;
import org.africa.bank.repository.DossierEERRepository;
import org.africa.bank.repository.PieceJustificativeRepository;
import org.africa.bank.service.PieceJustificativeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PieceJustificativeServiceImpl implements PieceJustificativeService {

    private final PieceJustificativeRepository pjRepository;
    private final DossierEERRepository dossierRepository;
    private final ModelMapper modelMapper;
    private final PieceJustificativeQueryService queryService;

    public PieceJustificativeServiceImpl(
            PieceJustificativeRepository pjRepository,
            DossierEERRepository dossierRepository,
            ModelMapper modelMapper,
            PieceJustificativeQueryService queryService) {
        this.pjRepository = pjRepository;
        this.dossierRepository = dossierRepository;
        this.modelMapper = modelMapper;
        this.queryService = queryService;
    }

    @Override
    public PieceJustificativeDTO attacherPJ(Long dossierId, PieceJustificativeDTO dto) {
        DossierEER dossier = dossierRepository.findById(dossierId)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier EER", dossierId));
        PieceJustificative pj = modelMapper.map(dto, PieceJustificative.class);
        pj.setDossierEER(dossier);
        pj.setAttache(true);
        return modelMapper.map(pjRepository.save(pj), PieceJustificativeDTO.class);
    }

    @Override
    public PieceJustificativeDTO updatePJ(Long pjId, PieceJustificativeDTO dto) {
        PieceJustificative existing = pjRepository.findById(pjId)
                .orElseThrow(() -> new ResourceNotFoundException("Pièce justificative", pjId));
        modelMapper.map(dto, existing);
        return modelMapper.map(pjRepository.save(existing), PieceJustificativeDTO.class);
    }

    @Override
    public void supprimerPJ(Long pjId) {
        if (!pjRepository.existsById(pjId)) {
            throw new ResourceNotFoundException("Pièce justificative", pjId);
        }
        pjRepository.deleteById(pjId);
    }

    // Délégation vers le service de lecture
    @Override
    public List<PieceJustificativeDTO> getPJParDossier(Long dossierId) {
        return queryService.getPJParDossier(dossierId);
    }

    @Override
    public List<PieceJustificativeDTO> getPJObligatoiresNonAttachees(Long dossierId) {
        return queryService.getPJObligatoiresNonAttachees(dossierId);
    }

    @Override
    public boolean toutesLesPJObligatoiresSontAttachees(Long dossierId) {
        return queryService.toutesLesPJObligatoiresSontAttachees(dossierId);
    }
}
