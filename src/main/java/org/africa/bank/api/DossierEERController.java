package org.africa.bank.api;

import org.africa.bank.dto.PersonneLMDTO;
import org.africa.bank.dto.PersonneLPhysiqueDTO;
import org.africa.bank.dto.TiersDTO;
import org.africa.bank.entity.DossierEER;
import org.africa.bank.service.workflow.WorkflowEERService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dossiers-eer")
@CrossOrigin(origins = "*")
public class DossierEERController {

    @Autowired
    private WorkflowEERService workflowService;

    /**
     * ÉTAPE 1 & 2 : Initialisation du dossier
     * Payload attendu: { "createur": "admin", "typePersonne": "PHY", "codeSiege": "001" }
     */
    @PostMapping("/initier")
    public ResponseEntity<DossierEER> initier(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(workflowService.initierDossier(params));
    }

    /**
     * ÉTAPE 3 : Ajout d'un Titulaire (Principal ou Co-titulaire)
     * On envoie le TiersDTO (id existant ou nouvelles données)
     */
    @PostMapping("/{id}/titulaires")
    public ResponseEntity<DossierEER> ajouterTitulaire(
            @PathVariable Long id,
            @RequestBody TiersDTO tiersDTO,
            @RequestParam(defaultValue = "false") boolean estCoTitulaire) {
        return ResponseEntity.ok(workflowService.ajouterTitulaire(id, tiersDTO, estCoTitulaire));
    }

    /**
     * ÉTAPE 4 : Ajout d'une Personne Liée Physique (PLP)
     */
    @PostMapping("/{id}/personnes-physiques")
    public ResponseEntity<DossierEER> ajouterPLP(
            @PathVariable Long id,
            @RequestBody PersonneLPhysiqueDTO dto) {
        return ResponseEntity.ok(workflowService.ajouterLienPhysique(id, dto));
    }

    /**
     * ÉTAPE 4 : Ajout d'une Personne Liée Morale (PLM)
     */
    @PostMapping("/{id}/personnes-morales")
    public ResponseEntity<DossierEER> ajouterPLM(
            @PathVariable Long id,
            @RequestBody PersonneLMDTO dto) {
        return ResponseEntity.ok(workflowService.ajouterLienMoral(id, dto));
    }

    /**
     * ÉTAPE FINALE : Validation et clôture du dossier
     */
    @PostMapping("/{id}/finaliser")
    public ResponseEntity<DossierEER> finaliser(@PathVariable Long id) {
        return ResponseEntity.ok(workflowService.finaliserDossier(id));
    }

    /**
     * RÉCUPÉRATION : Détails complets du dossier (Status, Historique, etc.)
     */
    @GetMapping("/{id}")
    public ResponseEntity<DossierEER> getDossier(@PathVariable Long id) {
        // Cette méthode doit être ajoutée dans WorkflowEERService si elle n'y est pas
        return ResponseEntity.ok(workflowService.getDossierById(id));
    }

    /**
     * RÉSUMÉ : Récupérer uniquement les statistiques/statut du dossier
     */
    @GetMapping("/{id}/statut")
    public ResponseEntity<Map<String, Object>> getStatut(@PathVariable Long id) {
        return ResponseEntity.ok(workflowService.getStatutWorkflow(id));
    }
}