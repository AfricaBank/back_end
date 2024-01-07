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
     * New Tiers.
     * Initialisation
     * @param model
     * @return
     */
    @RequestMapping("tier/new")
    public String newTiers(Model model) {
        model.addAttribute("tier", new Tiers());
        return "tierform";
    }

    /**
     * Save tiers to database.
     *
     * @param tiers
     * @return
     */
    @RequestMapping(value = "tier", method = RequestMethod.POST)
    public String saveTier(Tiers tiers) {
        titulaireDao.saveTier(tiers);
        return "redirect:/tier/" + tiers.getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", titulaireDao.getTiersById(id));
        return "tierform";
    }


}
