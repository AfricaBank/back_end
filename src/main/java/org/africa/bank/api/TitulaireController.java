package org.africa.bank.api;

import org.africa.bank.dao.entity.Tiers;
import org.africa.bank.dao.repository.TitulaireRepository;
import org.africa.bank.dao.service.TitulaireDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/tiers")
public class TitulaireController {

    private final TitulaireDao titulaireDao;
    @Autowired
    private TitulaireRepository titulaireRepository;


    public TitulaireController(TitulaireDao titulaireDao) {
        this.titulaireDao = titulaireDao;
    }


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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", titulaireDao.getTiersById(id));
        return "tierform";
    }

    @DeleteMapping("/{id}")
    public void deleteTiers(@PathVariable Long id) {
        titulaireRepository.deleteById(id);
    }


}
