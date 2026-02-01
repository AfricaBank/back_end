package org.africa.bank.api;

import org.africa.bank.service.workflow.WorkflowEERService;
import org.africa.bank.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workflow")
@CrossOrigin(origins = "*")
public class WorkflowEERController {

    @Autowired
    private WorkflowEERService workflowService;

    // 1. Initier un nouveau dossier
    @PostMapping("/initier")
    public ResponseEntity<DossierEER> initierDossier(
            @RequestBody Map<String, Object> params) {
        DossierEER dossier = workflowService.initierDossier(params);
        return ResponseEntity.ok(dossier);
    }

    // 2. Rechercher des Tiers existants
    @PostMapping("/{dossierId}/rechercher-tiers")
    public ResponseEntity<List<Tiers>> rechercherTiers(
            @PathVariable Long dossierId,
            @RequestBody Map<String, Object> criteres) {
        List<Tiers> resultats = workflowService.rechercherTiers(dossierId, criteres);
        return ResponseEntity.ok(resultats);
    }

    // 3. Ajouter un titulaire/co-titulaire
    @PostMapping("/{dossierId}/ajouter-titulaire")
    public ResponseEntity<DossierEER> ajouterTitulaire(
            @PathVariable Long dossierId,
            @RequestParam Long tiersId,
            @RequestParam(defaultValue = "false") boolean estCoTitulaire) {
        DossierEER dossier = workflowService.ajouterTitulaire(
                dossierId, tiersId, estCoTitulaire);
        return ResponseEntity.ok(dossier);
    }

    // 4. Ajouter une personne physique liée
    @PostMapping("/{dossierId}/ajouter-personne-physique")
    public ResponseEntity<DossierEER> ajouterPersonnePhysique(
            @PathVariable Long dossierId,
            @RequestBody PersonneLPhysique personne) {
        DossierEER dossier = workflowService.ajouterPersonnePhysique(dossierId, personne);
        return ResponseEntity.ok(dossier);
    }

    // 5. Ajouter une personne morale liée
    @PostMapping("/{dossierId}/ajouter-personne-morale")
    public ResponseEntity<DossierEER> ajouterPersonneMorale(
            @PathVariable Long dossierId,
            @RequestBody PersonneLM personne) {
        DossierEER dossier = workflowService.ajouterPersonneMorale(dossierId, personne);
        return ResponseEntity.ok(dossier);
    }

    // 6. Passer à l'étape suivante
    @PostMapping("/{dossierId}/etape-suivante")
    public ResponseEntity<DossierEER> passerEtapeSuivante(@PathVariable Long dossierId) {
        DossierEER dossier = workflowService.passerEtapeSuivante(dossierId);
        return ResponseEntity.ok(dossier);
    }

    // 7. Valider le dossier complet
    @PostMapping("/{dossierId}/valider")
    public ResponseEntity<DossierEER> validerDossier(@PathVariable Long dossierId) {
        DossierEER dossier = workflowService.validerDossier(dossierId);
        return ResponseEntity.ok(dossier);
    }

    // 8. Récupérer un dossier
    @GetMapping("/{dossierId}")
    public ResponseEntity<DossierEER> getDossier(@PathVariable Long dossierId) {
        DossierEER dossier = workflowService.getDossierById(dossierId);
        return ResponseEntity.ok(dossier);
    }

    // 9. Annuler un dossier
    @PostMapping("/{dossierId}/annuler")
    public ResponseEntity<DossierEER> annulerDossier(
            @PathVariable Long dossierId,
            @RequestParam String raison) {
        DossierEER dossier = workflowService.annulerDossier(dossierId, raison);
        return ResponseEntity.ok(dossier);
    }
}