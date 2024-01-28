package org.horizon.test.controllers;

import org.horizon.test.dao.entities.Titulaire;
import org.horizon.test.dao.services.Impl.TitulaireServiceImpl;
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
public class TitulaireRestController {

    private TitulaireServiceImpl titulaireService;

    @GetMapping("/titulaires/list")
    public ResponseEntity<Page<Titulaire>> getAllTitulaires(String nom, int page, int size) {
        return ResponseEntity.ok(titulaireService.getAllTitulaires(nom, page, size));
    }

    @GetMapping("/titulaires/{idTitulaire}")
    public ResponseEntity<Titulaire> getTitulaire( @PathVariable Long idTitulaire) {
        return ResponseEntity.ok(titulaireService.getTitulaire(idTitulaire));
    }

    @PostMapping("/titulaires/add")
    public ResponseEntity<?> addTitulaire(@Valid @RequestBody Titulaire titulaire, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(titulaireService.addTitulaire(titulaire));

    }


    @PutMapping("/titulaires/update")
    public ResponseEntity<?> updateTitulaire(@Valid @RequestBody Titulaire titulaire, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(titulaireService.updateTitulaire(titulaire));
    }

}
