package org.horizon.test.controllers;

import org.horizon.test.dao.entities.Coordonnee;
import org.horizon.test.dao.services.Impl.CoordonneeServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

import static org.horizon.test.utils.BindingErrors.getErrors;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class CoordonneeRestController {

    private CoordonneeServiceImpl coordonneeService;

    @GetMapping("/coordonnees/list")
    public ResponseEntity<Page<Coordonnee>> getAllCoordonnees(int page, int size) {
        return ResponseEntity.ok(coordonneeService.getAllCoordonnees(page, size));
    }

    @GetMapping("/coordonnees/{idCoordonnee}")
    public ResponseEntity<Coordonnee> getCoordonnee( @PathVariable Long idCoordonnee) {
        return ResponseEntity.ok(coordonneeService.getCoordonnee(idCoordonnee));
    }

    @PostMapping("/coordonnees/add")
    public ResponseEntity<?> addCoordonnee( Long idDossier, @Valid @RequestBody Coordonnee coordonnee, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(coordonneeService.addCoordonnee(idDossier, coordonnee));

    }


    @PutMapping("/coordonnees/update")
    public ResponseEntity<?> updateCoordonnee(@Valid @RequestBody Coordonnee coordonnee, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(coordonneeService.updateCoordonnee(coordonnee));
    }

}
