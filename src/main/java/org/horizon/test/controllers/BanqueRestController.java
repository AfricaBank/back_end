package org.horizon.test.controllers;

import org.horizon.test.dao.entities.Banque;
import org.horizon.test.dao.services.Impl.BanqueServiceImpl;
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
public class BanqueRestController {

    private BanqueServiceImpl banqueService;

    @GetMapping("/banques/list")
    public ResponseEntity<Page<Banque>> getAllBanques(Integer code, int page, int size) {
        return ResponseEntity.ok(banqueService.getAllBanques(code, page, size));
    }

    @GetMapping("/banques/{idBanque}")
    public ResponseEntity<Banque> getBanque( @PathVariable Long idBanque) {
        return ResponseEntity.ok(banqueService.getBanque(idBanque));
    }

    @PostMapping("/banques/add")
    public ResponseEntity<?> addBanque(@Valid @RequestBody Banque banque, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(banqueService.addBanque(banque));

    }


    @PutMapping("/banques/update")
    public ResponseEntity<?> updateBanque(@Valid @RequestBody Banque banque, BindingResult bindingResult) {
        ResponseEntity<List<String>> BAD_REQUEST = getErrors(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        return ResponseEntity.ok(banqueService.updateBanque(banque));
    }

}
