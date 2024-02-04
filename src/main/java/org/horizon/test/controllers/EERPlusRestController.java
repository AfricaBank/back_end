package org.horizon.test.controllers;

import org.horizon.test.dao.entities.EERPlus;
import org.horizon.test.dao.services.Impl.EERPlusServiceImpl;
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
public class EERPlusRestController {

    private EERPlusServiceImpl eerPlusService;

    @GetMapping("/eerPluss/list")
    public ResponseEntity<Page<EERPlus>> getAllEERPluss(int page, int size) {
        return ResponseEntity.ok(eerPlusService.getAllEERPluss(page, size));
    }

    @GetMapping("/eerPluss/{idEERPlus}")
    public ResponseEntity<EERPlus> getEERPlus( @PathVariable Long idEERPlus) {
        return ResponseEntity.ok(eerPlusService.getEERPlus(idEERPlus));
    }

    @PostMapping("/eerPluss/add")
    public ResponseEntity<?> addEERPlus( Long idDossier, @Valid @RequestBody EERPlus eerPlus, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(eerPlusService.addEERPlus(idDossier, eerPlus));

    }


    @PutMapping("/eerPluss/update")
    public ResponseEntity<?> updateEERPlus(@Valid @RequestBody EERPlus eerPlus, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(eerPlusService.updateEERPlus(eerPlus));
    }

}
