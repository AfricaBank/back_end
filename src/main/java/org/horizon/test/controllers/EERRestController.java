package org.horizon.test.controllers;

import org.horizon.test.dao.entities.EER;
import org.horizon.test.dao.services.Impl.EERServiceImpl;
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
public class EERRestController {

    private EERServiceImpl eerService;

    @GetMapping("/eers/list")
    public ResponseEntity<Page<EER>> getAllEERs(String nom, int page, int size) {
        return ResponseEntity.ok(eerService.getAllEERs(nom, page, size));
    }

    @GetMapping("/eers/{idEER}")
    public ResponseEntity<EER> getEER( @PathVariable Long idEER) {
        return ResponseEntity.ok(eerService.getEER(idEER));
    }

    @PostMapping("/eers/add")
    public ResponseEntity<?> addEER(@Valid @RequestBody EER eer, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(eerService.addEER(eer));

    }


    @PutMapping("/eers/update")
    public ResponseEntity<?> updateEER(@Valid @RequestBody EER eer, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(eerService.updateEER(eer));
    }

}
