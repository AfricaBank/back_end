package org.africa.bank.service.workflow;

import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.constants.StatutDossier;
import org.africa.bank.dto.*;
import org.africa.bank.entity.*;
import org.africa.bank.exception.ResourceNotFoundException;
import org.africa.bank.exception.WorkflowException;
import org.africa.bank.repository.DossierEERRepository;
import org.africa.bank.repository.TiersRepository;
import org.africa.bank.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkflowEERService {

    private final DossierEERRepository dossierRepository;
    private final TiersRepository tiersRepository;
    private final TiersService tiersService;
    private final PersonneLPhysiqueService personnePhysiqueService;
    private final PersonneLMService personneLMService;
    private final DossierNumberService dossierNumberService;
    private final PieceJustificativeService pjService;
    private final CRConseillerService crService;

    public WorkflowEERService(
            DossierEERRepository dossierRepository,
            TiersRepository tiersRepository,
            TiersService tiersService,
            PersonneLPhysiqueService personnePhysiqueService,
            PersonneLMService personneLMService,
            DossierNumberService dossierNumberService,
            PieceJustificativeService pjService,
            CRConseillerService crService) {
        this.dossierRepository       = dossierRepository;
        this.tiersRepository         = tiersRepository;
        this.tiersService            = tiersService;
        this.personnePhysiqueService = personnePhysiqueService;
        this.personneLMService       = personneLMService;
        this.dossierNumberService    = dossierNumberService;
        this.pjService               = pjService;
        this.crService               = crService;
    }

    // =========================================================================
    // ABANDON / REPRISE
    // =========================================================================

    @Transactional
    public DossierEER abandonnerDossier(Long dossierId) {
        DossierEER dossier = getDossierOuErreur(dossierId);

        if (dossier.getStatut() == StatutDossier.ANNULE) {
            throw new WorkflowException("Ce dossier est déjà abandonné.");
        }
        if (dossier.getStatut() == StatutDossier.VALIDE) {
            throw new WorkflowException(
                    "Un dossier validé ne peut pas être abandonné.");
        }

        dossier.setStatut(StatutDossier.ANNULE);
        dossier.setDateModification(LocalDateTime.now());
        ajouterHistorique(dossier, "Dossier abandonné par l'utilisateur.");
        return dossierRepository.save(dossier);
    }

    @Transactional
    public DossierEER reprendreDossier(Long dossierId) {
        DossierEER dossier = getDossierOuErreur(dossierId);

        if (dossier.getStatut() != StatutDossier.ANNULE) {
            throw new WorkflowException(
                    "Seul un dossier abandonné peut être repris.");
        }

        dossier.setStatut(StatutDossier.EN_COURS);
        dossier.setDateModification(LocalDateTime.now());
        ajouterHistorique(dossier, "Dossier repris par l'utilisateur.");
        return dossierRepository.save(dossier);
    }

    // =========================================================================
    // ÉTAPE 1 : Initialisation
    // =========================================================================

    @Transactional
    public DossierEER initierDossier(Map<String, Object> params) {
        DossierEER dossier = new DossierEER();
        dossier.setReferenceDossier(dossierNumberService.generateReference());
        dossier.setStatut(StatutDossier.EN_COURS);
        dossier.setEtapeActuelle(EtapeProcessus.RECHERCHE_PERSONNE);
        dossier.setDateCreation(LocalDateTime.now());
        dossier.setCreateur((String) params.getOrDefault("createur", "SYSTEM"));
        dossier.setTypePersonne((String) params.get("typePersonne"));
        dossier.setCodeSiege((String) params.get("codeSiege"));
        dossier.setNatureRelation((String) params.get("natureRelation"));
        dossier.setCodeExploitant((String) params.get("codeExploitant"));
        dossier.setNomExploitant((String) params.get("nomExploitant"));
        dossier.setCiviliteCollectivite((String) params.get("civiliteCollectivite"));
        dossier.setNomCollectivite((String) params.get("nomCollectivite"));

        DossierEER saved = dossierRepository.save(dossier);
        ajouterHistorique(saved, "Dossier initialisé — référence : "
                + saved.getReferenceDossier());
        return dossierRepository.save(saved);
    }

    // =========================================================================
    // ÉTAPE 2 : Sélection tiers existant
    // =========================================================================

    @Transactional
    public DossierEER selectionnerTiersExistant(Long dossierId, Long tiersId) {
        DossierEER dossier = getDossierOuErreur(dossierId);
        Tiers tiers = tiersRepository.findById(tiersId)
                .orElseThrow(() -> new ResourceNotFoundException("Tiers", tiersId));
        dossier.setTitulairePrincipal(tiers);
        dossier.setEtapeActuelle(EtapeProcessus.AJOUT_TITULAIRE);
        ajouterHistorique(dossier, "Tiers sélectionné : " + tiers.getNom());
        return dossierRepository.save(dossier);
    }

    // =========================================================================
    // ÉTAPE 3 : Titulaire
    // =========================================================================

    @Transactional
    public DossierEER ajouterTitulaire(Long dossierId, TiersDTO tiersDTO,
                                       boolean estCoTitulaire) {
        DossierEER dossier = getDossierOuErreur(dossierId);
        Tiers tiers = (tiersDTO.getId() != null)
                ? tiersRepository.findById(tiersDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tiers", tiersDTO.getId()))
                : tiersRepository.save(tiersService.convertToEntity(tiersDTO));

        validerUniciteRole(dossier, tiers, estCoTitulaire);

        if (estCoTitulaire) {
            if (!dossier.getCoTitulaires().contains(tiers)) {
                dossier.getCoTitulaires().add(tiers);
                ajouterHistorique(dossier, "Co-titulaire ajouté : " + tiers.getNom());
            }
        } else {
            dossier.setTitulairePrincipal(tiers);
            ajouterHistorique(dossier, "Titulaire principal : " + tiers.getNom());
        }

        dossier.setEtapeActuelle(EtapeProcessus.AJOUT_PERSONNES_LIEES);
        dossier.setDateModification(LocalDateTime.now());
        return dossierRepository.save(dossier);
    }

    // =========================================================================
    // ÉTAPE 4 : Personnes liées
    // =========================================================================

    @Transactional
    public DossierEER ajouterLienPhysique(Long dossierId,
                                          PersonneLPhysiqueDTO dto) {
        DossierEER dossier = getDossierOuErreur(dossierId);
        if (dossier.getTitulairePrincipal() == null
                && dossier.getCoTitulaires().isEmpty()) {
            throw new WorkflowException(
                    "Un titulaire doit être défini avant d'ajouter des personnes liées.");
        }
        Tiers tiers = (dto.getTiersReferenceId() != null)
                ? tiersRepository.findById(dto.getTiersReferenceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tiers", dto.getTiersReferenceId()))
                : tiersRepository.save(tiersService.convertToEntity(
                mapperTiersDepuisPlp(dto)));

        PersonneLPhysique plp = personnePhysiqueService.convertToEntity(dto);
        plp.setTiers(tiers);
        plp.setDossierEER(dossier);
        if (plp.getTypeRelation() == null && dto.getTypeRelation() != null) {
            plp.setTypeRelation(dto.getTypeRelation());
        }
        dossier.getPersonnesPhysiques().add(plp);
        ajouterHistorique(dossier, "PLP ajoutée (" + dto.getTypeRelation()
                + ") : " + tiers.getNom());
        dossier.setDateModification(LocalDateTime.now());
        return dossierRepository.save(dossier);
    }

    @Transactional
    public DossierEER ajouterLienMoral(Long dossierId, PersonneLMDTO dto) {
        DossierEER dossier = getDossierOuErreur(dossierId);
        if (dossier.getTitulairePrincipal() == null
                && dossier.getCoTitulaires().isEmpty()) {
            throw new WorkflowException(
                    "Un titulaire doit être défini avant d'ajouter des personnes liées.");
        }
        Tiers tiers = (dto.getTiersReferenceId() != null)
                ? tiersRepository.findById(dto.getTiersReferenceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tiers", dto.getTiersReferenceId()))
                : tiersRepository.save(tiersService.convertToEntity(
                mapperTiersDepuisPlm(dto)));

        PersonneLM plm = personneLMService.convertToEntity(dto);
        plm.setTiers(tiers);
        plm.setDossierEER(dossier);
        dossier.getPersonnesMorales().add(plm);
        ajouterHistorique(dossier, "PLM ajoutée (GARANT_PM) : " + tiers.getNom());
        dossier.setDateModification(LocalDateTime.now());
        return dossierRepository.save(dossier);
    }

    // =========================================================================
    // ÉTAPE 5 : PJ
    // =========================================================================

    @Transactional
    public DossierEER confirmerPJCompletes(Long dossierId) {
        DossierEER dossier = getDossierOuErreur(dossierId);
        if (!pjService.toutesLesPJObligatoiresSontAttachees(dossierId)) {
            String libelles = pjService.getPJObligatoiresNonAttachees(dossierId)
                    .stream().map(PieceJustificativeDTO::getLibelle)
                    .collect(Collectors.joining(", "));
            throw new WorkflowException("PJ obligatoires manquantes : " + libelles);
        }
        dossier.setEtapeActuelle(EtapeProcessus.CR_CONSEILLER);
        ajouterHistorique(dossier, "PJ complètes.");
        return dossierRepository.save(dossier);
    }

    // =========================================================================
    // ÉTAPE 7 : Finalisation
    // =========================================================================

    @Transactional
    public DossierEER finaliserDossier(Long dossierId) {
        DossierEER dossier = getDossierOuErreur(dossierId);
        if (dossier.getTitulairePrincipal() == null) {
            throw new WorkflowException("Titulaire principal requis.");
        }
        if (!pjService.toutesLesPJObligatoiresSontAttachees(dossierId)) {
            String libelles = pjService.getPJObligatoiresNonAttachees(dossierId)
                    .stream().map(PieceJustificativeDTO::getLibelle)
                    .collect(Collectors.joining(", "));
            throw new WorkflowException("PJ obligatoires manquantes : " + libelles);
        }
        if (!crService.crEstRenseigne(dossierId)) {
            throw new WorkflowException("CR entretien requis.");
        }
        dossier.setEtapeActuelle(EtapeProcessus.SOUMISSION_VALIDATION);
        dossier.setStatut(StatutDossier.VALIDE);
        dossier.setDateTerminaison(LocalDateTime.now());
        ajouterHistorique(dossier, "Dossier soumis.");
        return dossierRepository.save(dossier);
    }

    // =========================================================================
    // LECTURE
    // =========================================================================

    @Transactional(readOnly = true)
    public DossierEER getDossierById(Long id) {
        return getDossierOuErreur(id);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getStatutWorkflow(Long dossierId) {
        DossierEER dossier = getDossierOuErreur(dossierId);
        Map<String, Object> statut = new HashMap<>();
        statut.put("dossierId",             dossier.getId());
        statut.put("reference",             dossier.getReferenceDossier());
        statut.put("etapeActuelle",         dossier.getEtapeActuelle());
        statut.put("statut",                dossier.getStatut());
        statut.put("createur",              dossier.getCreateur());
        statut.put("dateCreation",          dossier.getDateCreation());
        statut.put("hasTitulairePrincipal", dossier.getTitulairePrincipal() != null);
        statut.put("nbCoTitulaires",        dossier.getCoTitulaires().size());
        statut.put("nbPersonnesPhysiques",  dossier.getPersonnesPhysiques().size());
        statut.put("nbPersonnesMorales",    dossier.getPersonnesMorales().size());
        statut.put("pjCompletes",
                pjService.toutesLesPJObligatoiresSontAttachees(dossierId));
        statut.put("crRenseigne",
                crService.crEstRenseigne(dossierId));
        return statut;
    }

    // =========================================================================
    // UTILITAIRES
    // =========================================================================

    private DossierEER getDossierOuErreur(Long id) {
        return dossierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier EER", id));
    }

    private void ajouterHistorique(DossierEER dossier, String message) {
        if (dossier.getHistoriqueEtapes() == null)
            dossier.setHistoriqueEtapes(new ArrayList<>());
        HistoriqueEtape h = new HistoriqueEtape();
        h.setDatePassage(LocalDateTime.now());
        h.setCommentaire(message);
        h.setUtilisateur(dossier.getCreateur());
        h.setActionEffectuee("WORKFLOW_STEP");
        h.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(h);
    }

    private void validerUniciteRole(DossierEER dossier, Tiers tiers,
                                    boolean estCoTitulaire) {
        if (estCoTitulaire && dossier.getTitulairePrincipal() != null
                && dossier.getTitulairePrincipal().getId().equals(tiers.getId())) {
            throw new WorkflowException("Ce tiers est déjà titulaire principal.");
        }
        if (!estCoTitulaire && dossier.getCoTitulaires().stream()
                .anyMatch(ct -> ct.getId().equals(tiers.getId()))) {
            throw new WorkflowException("Ce tiers est déjà co-titulaire.");
        }
    }

    private TiersDTO mapperTiersDepuisPlp(PersonneLPhysiqueDTO plp) {
        TiersDTO t = new TiersDTO();
        t.setNom(plp.getNomFamille());
        t.setPrenom(plp.getPrenom());
        t.setTypeTiers("PHYSIQUE");
        return t;
    }

    private TiersDTO mapperTiersDepuisPlm(PersonneLMDTO plm) {
        TiersDTO t = new TiersDTO();
        t.setNom(plm.getRaisonSociale());
        t.setTypeTiers("MORALE");
        return t;
    }
}