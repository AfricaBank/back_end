package org.africa.bank.api;

import jakarta.validation.Valid;
import org.africa.bank.dto.PersonneLPhysiqueDTO;
import org.africa.bank.service.PersonneLPhysiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/personnes-physiques")
@CrossOrigin(origins = "*")
public class PersonneLPhysiqueController {

    @Autowired
    private PersonneLPhysiqueService personneService;

    @PostMapping
    public ResponseEntity<PersonneLPhysiqueDTO> createPersonne(
            @Valid @RequestBody PersonneLPhysiqueDTO personneDTO) {
        PersonneLPhysiqueDTO createdPersonne = personneService.createPersonne(personneDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonneLPhysiqueDTO> updatePersonne(
            @PathVariable Long id,
            @Valid @RequestBody PersonneLPhysiqueDTO personneDTO) {
        PersonneLPhysiqueDTO updatedPersonne = personneService.updatePersonne(id, personneDTO);
        return ResponseEntity.ok(updatedPersonne);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneLPhysiqueDTO> getPersonneById(@PathVariable Long id) {
        PersonneLPhysiqueDTO personneDTO = personneService.getPersonneById(id);
        return ResponseEntity.ok(personneDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PersonneLPhysiqueDTO>> getAllPersonnes(Pageable pageable) {
        Page<PersonneLPhysiqueDTO> personnesPage = personneService.getAllPersonnes(pageable);
        return ResponseEntity.ok(personnesPage);
    }

    @GetMapping("/dossier/{dossierEERId}")
    public ResponseEntity<List<PersonneLPhysiqueDTO>> getByDossierEER(@PathVariable Long dossierEERId) {
        List<PersonneLPhysiqueDTO> personnes = personneService.getPersonnesByDossierEER(dossierEERId);
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/tiers/{tiersId}")
    public ResponseEntity<List<PersonneLPhysiqueDTO>> getByTiersReference(@PathVariable Long tiersId) {
        List<PersonneLPhysiqueDTO> personnes = personneService.getPersonnesByTiersReference(tiersId);
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PersonneLPhysiqueDTO>> searchByNomPrenom(
            @RequestParam String nom,
            @RequestParam String prenom) {
        List<PersonneLPhysiqueDTO> results = personneService.searchByNomPrenom(nom, prenom);
        return ResponseEntity.ok(results);
    }
}