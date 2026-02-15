package org.africa.bank.api;

import jakarta.validation.Valid;
import org.africa.bank.dto.PersonneLMDTO;
import org.africa.bank.service.PersonneLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/personnes-morales")
@CrossOrigin(origins = "*")
public class PersonneLMController {

    @Autowired
    private PersonneLMService personneService;

    @PostMapping
    public ResponseEntity<PersonneLMDTO> createPersonneMorale(
            @Valid @RequestBody PersonneLMDTO personneDTO) {
        PersonneLMDTO createdPersonne = personneService.createPersonneMorale(personneDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonneLMDTO> updatePersonneMorale(
            @PathVariable Long id,
            @Valid @RequestBody PersonneLMDTO personneDTO) {
        PersonneLMDTO updatedPersonne = personneService.updatePersonneMorale(id, personneDTO);
        return ResponseEntity.ok(updatedPersonne);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonneMorale(@PathVariable Long id) {
        personneService.deletePersonneMorale(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneLMDTO> getPersonneMoraleById(@PathVariable Long id) {
        PersonneLMDTO personneDTO = personneService.getPersonneMoraleById(id);
        return ResponseEntity.ok(personneDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PersonneLMDTO>> getAllPersonnesMorales(Pageable pageable) {
        Page<PersonneLMDTO> personnesPage = personneService.getAllPersonnesMorales(pageable);
        return ResponseEntity.ok(personnesPage);
    }

    @GetMapping("/dossier/{dossierEERId}")
    public ResponseEntity<List<PersonneLMDTO>> getByDossierEER(@PathVariable Long dossierEERId) {
        List<PersonneLMDTO> personnes = personneService.getPersonnesMoralesByDossierEER(dossierEERId);
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/tiers/{tiersId}")
    public ResponseEntity<List<PersonneLMDTO>> getByTiersReference(@PathVariable Long tiersId) {
        List<PersonneLMDTO> personnes = personneService.getPersonnesMoralesByTiersReference(tiersId);
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/search/raison-sociale")
    public ResponseEntity<List<PersonneLMDTO>> searchByRaisonSociale(
            @RequestParam String raisonSociale) {
        List<PersonneLMDTO> results = personneService.searchByRaisonSociale(raisonSociale);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/forme-juridique/{formeJuridique}")
    public ResponseEntity<List<PersonneLMDTO>> getByFormeJuridique(
            @PathVariable String formeJuridique) {
        List<PersonneLMDTO> results = personneService.findByFormeJuridique(formeJuridique);
        return ResponseEntity.ok(results);
    }
}
