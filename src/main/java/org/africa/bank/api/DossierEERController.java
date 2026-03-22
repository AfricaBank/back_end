package org.africa.bank.api;

import jakarta.validation.Valid;
import org.africa.bank.dto.*;
import org.africa.bank.entity.DossierEER;
import org.africa.bank.service.CRConseillerService;
import org.africa.bank.service.PieceJustificativeService;
import org.africa.bank.service.RecherchePersonneService;
import org.africa.bank.service.workflow.WorkflowEERService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dossiers-eer")
public class DossierEERController {

    private final WorkflowEERService workflowService;
    private final RecherchePersonneService rechercheService;
    private final PieceJustificativeService pjService;
    private final CRConseillerService crService;

    public DossierEERController(
            WorkflowEERService workflowService,
            RecherchePersonneService rechercheService,
            PieceJustificativeService pjService,
            CRConseillerService crService) {
        this.workflowService  = workflowService;
        this.rechercheService = rechercheService;
        this.pjService        = pjService;
        this.crService        = crService;
    }

    // ── Étape 1 : Initialisation ──────────────────────────────────────────────
    @PostMapping("/initier")
    public ResponseEntity<DossierEER> initier(@RequestBody Map<String, Object> params) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workflowService.initierDossier(params));
    }

    // ── Étape 2 : Recherche tiers ─────────────────────────────────────────────
    @PostMapping("/rechercher-personne")
    public ResponseEntity<RecherchePersonneResponse> rechercherPersonne(
            @RequestBody RecherchePersonneRequest request) {
        return ResponseEntity.ok(rechercheService.rechercherPersonne(request));
    }

    @PostMapping("/{id}/selectionner-tiers/{tiersId}")
    public ResponseEntity<DossierEER> selectionnerTiers(
            @PathVariable Long id,
            @PathVariable Long tiersId) {
        return ResponseEntity.ok(workflowService.selectionnerTiersExistant(id, tiersId));
    }

    // ── Étape 3 : Titulaire ───────────────────────────────────────────────────
    @PostMapping("/{id}/titulaires")
    public ResponseEntity<DossierEER> ajouterTitulaire(
            @PathVariable Long id,
            @Valid @RequestBody TiersDTO tiersDTO,
            @RequestParam(defaultValue = "false") boolean estCoTitulaire) {
        return ResponseEntity.ok(
                workflowService.ajouterTitulaire(id, tiersDTO, estCoTitulaire));
    }

    // ── Étape 4 : Personnes liées ─────────────────────────────────────────────
    @PostMapping("/{id}/personnes-physiques")
    public ResponseEntity<DossierEER> ajouterPLP(
            @PathVariable Long id,
            @Valid @RequestBody PersonneLPhysiqueDTO dto) {
        return ResponseEntity.ok(workflowService.ajouterLienPhysique(id, dto));
    }

    @PostMapping("/{id}/personnes-morales")
    public ResponseEntity<DossierEER> ajouterPLM(
            @PathVariable Long id,
            @Valid @RequestBody PersonneLMDTO dto) {
        return ResponseEntity.ok(workflowService.ajouterLienMoral(id, dto));
    }

    // ── Étape 5 : Pièces justificatives ──────────────────────────────────────
    @PostMapping("/{id}/pieces-justificatives")
    public ResponseEntity<PieceJustificativeDTO> attacherPJ(
            @PathVariable Long id,
            @Valid @RequestBody PieceJustificativeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pjService.attacherPJ(id, dto));
    }

    @GetMapping("/{id}/pieces-justificatives")
    public ResponseEntity<List<PieceJustificativeDTO>> getPJ(@PathVariable Long id) {
        return ResponseEntity.ok(pjService.getPJParDossier(id));
    }

    @DeleteMapping("/{id}/pieces-justificatives/{pjId}")
    public ResponseEntity<Void> supprimerPJ(
            @PathVariable Long id,
            @PathVariable Long pjId) {
        pjService.supprimerPJ(pjId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/confirmer-pj")
    public ResponseEntity<DossierEER> confirmerPJ(@PathVariable Long id) {
        return ResponseEntity.ok(workflowService.confirmerPJCompletes(id));
    }

    // ── Étape 6 : CR Conseiller ───────────────────────────────────────────────
    @PostMapping("/{id}/cr-conseiller")
    public ResponseEntity<CRConseillerDTO> sauvegarderCR(
            @PathVariable Long id,
            @Valid @RequestBody CRConseillerDTO dto) {
        return ResponseEntity.ok(crService.sauvegarderCR(id, dto));
    }

    @GetMapping("/{id}/cr-conseiller")
    public ResponseEntity<CRConseillerDTO> getCR(@PathVariable Long id) {
        return ResponseEntity.ok(crService.getCRParDossier(id));
    }

    // ── Étape 7 : Finalisation ────────────────────────────────────────────────
    @PostMapping("/{id}/finaliser")
    public ResponseEntity<DossierEER> finaliser(@PathVariable Long id) {
        return ResponseEntity.ok(workflowService.finaliserDossier(id));
    }

    // ── Lecture ───────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<DossierEER> getDossier(@PathVariable Long id) {
        return ResponseEntity.ok(workflowService.getDossierById(id));
    }

    @GetMapping("/{id}/statut")
    public ResponseEntity<Map<String, Object>> getStatut(@PathVariable Long id) {
        return ResponseEntity.ok(workflowService.getStatutWorkflow(id));
    }
}