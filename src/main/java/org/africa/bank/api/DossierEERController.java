package org.africa.bank.api;

import org.africa.bank.service.workflow.WorkflowEERService;
import org.africa.bank.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/eer")
public class DossierEERController {

    @Autowired
    private WorkflowEERService workflowService;

    @PostMapping("/initier")
    public ResponseEntity<DossierEER> initierDossier(@RequestBody Map<String, Object> params) {
        DossierEER dossier = workflowService.initierDossier(params);
        return ResponseEntity.ok(dossier);
    }

    @PostMapping("/{id}/etape-suivante")
    public ResponseEntity<DossierEER> passerEtapeSuivante(@PathVariable Long id) {
        DossierEER dossier = workflowService.passerEtapeSuivante(id);
        return ResponseEntity.ok(dossier);
    }

    @PostMapping("/{id}/titulaires")
    public ResponseEntity<DossierEER> ajouterTitulaire(
            @PathVariable Long id,
            @RequestParam Long tiersId,
            @RequestParam boolean estCoTitulaire) {
        DossierEER dossier = workflowService.ajouterTitulaire(id, tiersId, estCoTitulaire);
        return ResponseEntity.ok(dossier);
    }

    @PostMapping("/{id}/personnes-physiques")
    public ResponseEntity<DossierEER> ajouterPersonnePhysique(
            @PathVariable Long id,
            @RequestBody PersonneLPhysique personne) {
        DossierEER dossier = workflowService.ajouterPersonnePhysique(id, personne);
        return ResponseEntity.ok(dossier);
    }

    @PostMapping("/{id}/personnes-morales")
    public ResponseEntity<DossierEER> ajouterPersonneMorale(
            @PathVariable Long id,
            @RequestBody PersonneLM personne) {
        DossierEER dossier = workflowService.ajouterPersonneMorale(id, personne);
        return ResponseEntity.ok(dossier);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DossierEER> getDossier(@PathVariable Long id) {
        DossierEER dossier = workflowService.getDossierById(id);
        return ResponseEntity.ok(dossier);
    }
}
