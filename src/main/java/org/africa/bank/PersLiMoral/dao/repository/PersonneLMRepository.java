package org.africa.bank.PersLiMoral.dao.repository;

import org.africa.bank.PersLiMoral.dao.entities.PersonneLM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneLMRepository extends JpaRepository<PersonneLM, Long> {
    Page<PersonneLM> findPersonneLMByCodeExploitantContains(String codeExploitant, Pageable page);
}
