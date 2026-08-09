package org.africa.bank.service;

import org.africa.bank.dto.AvisDecisionDTO;

public interface AvisDecisionService {
    AvisDecisionDTO soumettreAvis(Long dossierId, AvisDecisionDTO dto);
    AvisDecisionDTO getAvisParDossier(Long dossierId);
}