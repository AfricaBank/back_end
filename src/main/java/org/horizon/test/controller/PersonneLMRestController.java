package org.horizon.test.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.PersonneLM;
import org.horizon.test.dao.services.impl.PersonneLMServiceImpl;
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
public class PersonneLMRestController {

    private PersonneLMServiceImpl plmService;

    @GetMapping("/plm/list")
    public ResponseEntity<Page<PersonneLM>> getAllPersonneLM(String codeExploitant, int page, int size) {
        return ResponseEntity.ok(plmService.getAllPersonneLM(codeExploitant, page, size));
    }

    @GetMapping("/plm/{idPersonneLM}")
    public ResponseEntity<PersonneLM> getPersonneLM( @PathVariable Long idPersonneLM) {
        return ResponseEntity.ok(plmService.getPersonneLM(idPersonneLM));
    }

    @PostMapping("/plm/add")
    public ResponseEntity<?> addPersonneLM(@Valid @RequestBody PersonneLM plm, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(plmService.addPersonneLM(plm));
    }


    @PutMapping("/plm/update")
    public ResponseEntity<?> updatePersonneLM(@Valid @RequestBody PersonneLM plm, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(plmService.updatePersonneLM(plm));
    }
}
