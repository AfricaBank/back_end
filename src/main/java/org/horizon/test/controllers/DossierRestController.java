package org.horizon.test.controllers;

import org.horizon.test.dao.entities.Dossier;
import org.horizon.test.dao.services.Impl.DossierServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.horizon.test.utils.BindingErrors.getErrors;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class DossierRestController {

    private DossierServiceImpl dossierService;

    @GetMapping("/dossiers/list")
    public ResponseEntity<Page<Dossier>> getAllDossiers(String nom, int page, int size) {
        return ResponseEntity.ok(dossierService.getAllDossiers(nom, page, size));
    }

    @GetMapping("/dossiers/{idDossier}")
    public ResponseEntity<Dossier> getDossier( @PathVariable Long idDossier) {
        return ResponseEntity.ok(dossierService.getDossier(idDossier));
    }

    @PostMapping("/dossiers/add")
    public ResponseEntity<?> addDossier(@Valid @RequestBody Dossier dossier, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(dossierService.addDossier(dossier));

    }


    @PutMapping("/dossiers/update")
    public ResponseEntity<?> updateDossier(@Valid @RequestBody Dossier dossier, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(dossierService.updateDossier(dossier));
    }

}
