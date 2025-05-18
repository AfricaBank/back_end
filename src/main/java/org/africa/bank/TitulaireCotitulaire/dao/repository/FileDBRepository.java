package org.africa.bank.TitulaireCotitulaire.dao.repository;


import org.africa.bank.TitulaireCotitulaire.dao.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
