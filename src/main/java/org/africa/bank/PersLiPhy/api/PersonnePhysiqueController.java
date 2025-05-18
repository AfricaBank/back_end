package org.africa.bank.PersLiPhy.api;

import org.africa.bank.PersLiPhy.dao.PersonnePhysiqueDAO;
import org.africa.bank.PersLiPhy.entity.PersonneLPhysique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personneLPhysique")
public class PersonnePhysiqueController {

    @Autowired
    private PersonnePhysiqueDAO personneLPhysiqueDAO;

    @PostMapping("/save")
    public void savePersonneLPhysique(@RequestBody PersonneLPhysique personneLPhysique) {
        personneLPhysiqueDAO.savePersonneLPhysique(personneLPhysique);
    }

    @GetMapping("/get/{id}")
    public PersonneLPhysique getPersonneLPhysiqueById(@PathVariable Long id) {
        return personneLPhysiqueDAO.getPersonneLPhysiqueById(id);
    }

    @GetMapping("/getAll")
    public List<PersonneLPhysique> getAllPersonnesLPhysiques() {
        return personneLPhysiqueDAO.getAllPersonnesLPhysiques();
    }

    @PutMapping("/update")
    public void updatePersonneLPhysique(@RequestBody PersonneLPhysique personneLPhysique) {
        personneLPhysiqueDAO.updatePersonneLPhysique(personneLPhysique);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePersonneLPhysique(@PathVariable Long id) {
        personneLPhysiqueDAO.deletePersonneLPhysique(id);
    }
}
