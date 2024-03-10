package org.horizon.test.dao.services.impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.PersonneLM;
import org.horizon.test.dao.repositories.PersonneLMRepository;
import org.horizon.test.dao.services.PersonneLMService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonneLMServiceImpl implements PersonneLMService {
    private PersonneLMRepository personneLMRepository;
    @Override
    public Page<PersonneLM> getAllPersonneLM(String codeExploitant, int page, int size) {
        return personneLMRepository.findPersonneLMByCodeExploitantContains(codeExploitant, PageRequest.of(page, size));
    }

    @Override
    public PersonneLM getPersonneLM(Long idPersonneLM) {
        return personneLMRepository.findById(idPersonneLM).orElse(null);
    }

    @Override
    public PersonneLM addPersonneLM(PersonneLM personneLM) {
        return personneLMRepository.save(personneLM);
    }

    @Override
    public PersonneLM updatePersonneLM(PersonneLM personneLM) {
        return personneLMRepository.save(personneLM);
    }
}
