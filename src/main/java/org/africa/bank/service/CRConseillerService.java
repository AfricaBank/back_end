package org.africa.bank.service;

import org.africa.bank.dto.CRConseillerDTO;

public interface CRConseillerService {
    CRConseillerDTO sauvegarderCR(Long dossierId, CRConseillerDTO dto);
    CRConseillerDTO getCRParDossier(Long dossierId);
    boolean crEstRenseigne(Long dossierId);
}