package org.africa.bank.service;

import org.africa.bank.dto.PieceJustificativeDTO;
import java.util.List;

public interface PieceJustificativeService {
    PieceJustificativeDTO attacherPJ(Long dossierId, PieceJustificativeDTO dto);
    PieceJustificativeDTO updatePJ(Long pjId, PieceJustificativeDTO dto);
    void supprimerPJ(Long pjId);
    List<PieceJustificativeDTO> getPJParDossier(Long dossierId);
    List<PieceJustificativeDTO> getPJObligatoiresNonAttachees(Long dossierId);
    boolean toutesLesPJObligatoiresSontAttachees(Long dossierId);
}