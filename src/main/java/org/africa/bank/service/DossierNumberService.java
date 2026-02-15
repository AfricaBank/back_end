package org.africa.bank.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class DossierNumberService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String generateReference() {
        int year = LocalDateTime.now().getYear();

        // Utilise une séquence PostgreSQL (à créer en base : CREATE SEQUENCE eer_sequence START 1)
        // Cela garantit l'unicité même si 10 banquiers créent un dossier en même temps
        Query query = entityManager.createNativeQuery("SELECT nextval('eer_sequence')");
        Long seq = ((Number) query.getSingleResult()).longValue();

        // Format : EER-2026-001 (le %03d force 3 chiffres minimum)
        return String.format("EER-%d-%03d", year, seq);
    }
}