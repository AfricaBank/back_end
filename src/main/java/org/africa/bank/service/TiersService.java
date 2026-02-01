package org.africa.bank.service;

import org.africa.bank.dto.TiersDTO;
import org.africa.bank.entity.Tiers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

public interface TiersService {

    // CRUD basique
    TiersDTO createTiers(TiersDTO tiersDTO);
    TiersDTO updateTiers(Long id, TiersDTO tiersDTO);
    void deleteTiers(Long id);
    TiersDTO getTiersById(Long id);
    Page<TiersDTO> getAllTiers(Pageable pageable);

    // Recherche avancée
    List<TiersDTO> searchTiers(Map<String, Object> searchCriteria);

    // Recherche par différents critères
    List<TiersDTO> findByNom(String nom);
    List<TiersDTO> findByPrenom(String prenom);
    List<TiersDTO> findByNomAndPrenom(String nom, String prenom);
    TiersDTO findByEmail(String email);
    TiersDTO findByMobile(String mobile);
    List<TiersDTO> findByTypeTiers(String typeTiers);

    // Validation
    boolean isTiersExists(Long id);
    boolean isEmailExists(String email);
    boolean isMobileExists(String mobile);
    boolean isNumeroIdentifiantFiscalExists(String numero);

    // Conversion
    TiersDTO convertToDTO(Tiers tiers);
    Tiers convertToEntity(TiersDTO tiersDTO);

    // Pour le workflow
    Tiers getEntityById(Long id);
    List<Tiers> searchEntities(Map<String, Object> criteria);
}