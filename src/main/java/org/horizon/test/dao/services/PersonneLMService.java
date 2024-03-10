package org.horizon.test.dao.services;

import org.horizon.test.dao.entities.PersonneLM;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PersonneLMService {

    Page<PersonneLM> getAllPersonneLM(String codeExploitant, int page, int size);

    PersonneLM getPersonneLM(Long idPersonneLM);

    PersonneLM addPersonneLM(PersonneLM personneLM);

    PersonneLM updatePersonneLM(PersonneLM personneLM);
}
