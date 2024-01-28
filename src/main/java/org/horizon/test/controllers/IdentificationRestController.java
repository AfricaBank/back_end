package org.horizon.test.controllers;

import org.horizon.test.dao.entities.Identification;
import org.horizon.test.dao.services.Impl.IdentificationServiceImpl;
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
public class IdentificationRestController {

    private IdentificationServiceImpl identificationService;

    @GetMapping("/identifications/list")
    public ResponseEntity<Page<Identification>> getAllIdentifications(String nom, int page, int size) {
        return ResponseEntity.ok(identificationService.getAllIdentifications(nom, page, size));
    }

    @GetMapping("/identifications/{idIdentification}")
    public ResponseEntity<Identification> getIdentification( @PathVariable Long idIdentification) {
        return ResponseEntity.ok(identificationService.getIdentification(idIdentification));
    }

    @PostMapping("/identifications/add")
    public ResponseEntity<?> addIdentification(@Valid @RequestBody Identification identification, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(identificationService.addIdentification(identification));

    }


    @PutMapping("/identifications/update")
    public ResponseEntity<?> updateIdentification(@Valid @RequestBody Identification identification, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(identificationService.updateIdentification(identification));
    }

}
