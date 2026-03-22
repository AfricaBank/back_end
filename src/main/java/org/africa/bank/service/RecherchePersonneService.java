package org.africa.bank.service;

import org.africa.bank.dto.RecherchePersonneRequest;
import org.africa.bank.dto.RecherchePersonneResponse;
import org.africa.bank.dto.TiersDTO;
import org.africa.bank.entity.DossierEER;
import org.africa.bank.entity.Tiers;
import org.africa.bank.repository.DossierEERRepository;
import org.africa.bank.repository.TiersRepository;
import org.africa.bank.specification.TiersSpecification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service dédié à la recherche de personnes.
 * Séparé de WorkflowEERService pour éviter les problèmes
 * de proxy CGLIB liés à @Transactional(readOnly = true).
 */
@Service
public class RecherchePersonneService {

    private final DossierEERRepository dossierRepository;
    private final TiersRepository tiersRepository;
    private final TiersService tiersService;

    public RecherchePersonneService(
            DossierEERRepository dossierRepository,
            TiersRepository tiersRepository,
            TiersService tiersService) {
        this.dossierRepository = dossierRepository;
        this.tiersRepository   = tiersRepository;
        this.tiersService      = tiersService;
    }

    public RecherchePersonneResponse rechercherPersonne(
            RecherchePersonneRequest request) {

        Map<String, Object> criteres = buildCriteres(request);

        // Bloc 1 : dossiers en cours dans MINSA
        List<TiersDTO> dossiersEnCours = dossierRepository.findDossiersEnCours()
                .stream()
                .map(DossierEER::getTitulairePrincipal)
                .filter(Objects::nonNull)
                .filter(t -> matchesCriteres(t, request))
                .map(tiersService::convertToDTO)
                .collect(Collectors.toList());

        // Bloc 2 : tiers existants dans ATLAS
        List<TiersDTO> tiersExistants = tiersRepository
                .findAll(TiersSpecification.avecCriteres(criteres))
                .stream()
                .map(tiersService::convertToDTO)
                .collect(Collectors.toList());

        return new RecherchePersonneResponse(dossiersEnCours, tiersExistants, true);
    }

    private Map<String, Object> buildCriteres(RecherchePersonneRequest req) {
        Map<String, Object> criteres = new HashMap<>();
        if (req.getNom()        != null) criteres.put("nom",        req.getNom());
        if (req.getPrenom()     != null) criteres.put("prenom",     req.getPrenom());
        if (req.getIdNational() != null) criteres.put("idNational", req.getIdNational());
        if (req.getNomBE()      != null) criteres.put("nom",        req.getNomBE());
        if (req.getPrenomBE()   != null) criteres.put("prenom",     req.getPrenomBE());
        return criteres;
    }

    private boolean matchesCriteres(Tiers t, RecherchePersonneRequest req) {
        if (req.getNom() != null
                && (t.getNom() == null
                || !t.getNom().toLowerCase()
                .contains(req.getNom().toLowerCase()))) {
            return false;
        }
        if (req.getPrenom() != null
                && (t.getPrenom() == null
                || !t.getPrenom().toLowerCase()
                .contains(req.getPrenom().toLowerCase()))) {
            return false;
        }
        return true;
    }
}