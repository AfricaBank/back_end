package org.africa.bank.api;

import jakarta.validation.Valid;
import org.africa.bank.dto.TiersDTO;
import org.africa.bank.service.TiersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tiers")
@CrossOrigin(origins = "*")
public class TiersController {

    @Autowired
    private TiersService tiersService;

    @PostMapping
    public ResponseEntity<TiersDTO> createTiers(@Valid @RequestBody TiersDTO tiersDTO) {
        TiersDTO createdTiers = tiersService.createTiers(tiersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTiers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TiersDTO> updateTiers(
            @PathVariable Long id,
            @Valid @RequestBody TiersDTO tiersDTO) {
        TiersDTO updatedTiers = tiersService.updateTiers(id, tiersDTO);
        return ResponseEntity.ok(updatedTiers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTiers(@PathVariable Long id) {
        tiersService.deleteTiers(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TiersDTO> getTiersById(@PathVariable Long id) {
        TiersDTO tiersDTO = tiersService.getTiersById(id);
        return ResponseEntity.ok(tiersDTO);
    }

    @GetMapping
    public ResponseEntity<Page<TiersDTO>> getAllTiers(Pageable pageable) {
        Page<TiersDTO> tiersPage = tiersService.getAllTiers(pageable);
        return ResponseEntity.ok(tiersPage);
    }

    @PostMapping("/search")
    public ResponseEntity<List<TiersDTO>> searchTiers(@RequestBody Map<String, Object> searchCriteria) {
        List<TiersDTO> results = tiersService.searchTiers(searchCriteria);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search/nom")
    public ResponseEntity<List<TiersDTO>> searchByNom(@RequestParam String nom) {
        List<TiersDTO> results = tiersService.findByNom(nom);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search/nom-prenom")
    public ResponseEntity<List<TiersDTO>> searchByNomAndPrenom(
            @RequestParam String nom,
            @RequestParam String prenom) {
        List<TiersDTO> results = tiersService.findByNomAndPrenom(nom, prenom);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<TiersDTO> searchByEmail(@PathVariable String email) {
        TiersDTO tiersDTO = tiersService.findByEmail(email);
        return ResponseEntity.ok(tiersDTO);
    }

    @GetMapping("/search/mobile/{mobile}")
    public ResponseEntity<TiersDTO> searchByMobile(@PathVariable String mobile) {
        TiersDTO tiersDTO = tiersService.findByMobile(mobile);
        return ResponseEntity.ok(tiersDTO);
    }

    @GetMapping("/type/{typeTiers}")
    public ResponseEntity<List<TiersDTO>> getByTypeTiers(@PathVariable String typeTiers) {
        List<TiersDTO> results = tiersService.findByTypeTiers(typeTiers);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String email) {
        boolean exists = tiersService.isEmailExists(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/mobile/{mobile}")
    public ResponseEntity<Map<String, Boolean>> checkMobileExists(@PathVariable String mobile) {
        boolean exists = tiersService.isMobileExists(mobile);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
}