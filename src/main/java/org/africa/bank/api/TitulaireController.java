package org.africa.bank.api;

import org.africa.bank.dao.entity.Tiers;
import org.africa.bank.dao.repository.TitulaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/tiers")
public class TitulaireController {

    @Autowired
    private TitulaireRepository titulaireRepository;


    /**
     * Save tiers to database.
     *
     * @param
     * @return
     */

    @PostMapping
    public Tiers createTiers(@RequestBody Tiers tiers) {
        return titulaireRepository.save(tiers);
    }

    @GetMapping
    public List<Tiers> getAllTiers() {
        return titulaireRepository.findAll();
    }

    /**
     * get titular bay his id
     *
     * @param
     * @return
     */

    @GetMapping("/{id}")
    public Tiers getTiersById(@PathVariable Long id) {
        return titulaireRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Tiers updateTiers(@PathVariable Long id, @RequestBody Tiers updatedTiers) {
        updatedTiers.setId(id);
        return titulaireRepository.save(updatedTiers);
    }

    @DeleteMapping("/{id}")
    public void deleteTiers(@PathVariable Long id) {
        titulaireRepository.deleteById(id);
    }


}
