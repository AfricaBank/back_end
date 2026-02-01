package org.africa.bank.service;

import org.africa.bank.entity.Tiers;
import org.africa.bank.repository.TiersRepository;
import org.africa.bank.specification.TiersSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RechercheTiersService {

    @Autowired
    private TiersRepository tiersRepository;

    public List<Tiers> rechercherTiers(Map<String, Object> criteres) {

        // Si aucun critère n'est fourni, retourner tous les tiers
        if (criteres == null || criteres.isEmpty()) {
            return tiersRepository.findAll();
        }

        // Utiliser les Specifications pour une recherche dynamique
        Specification<Tiers> spec = TiersSpecification.avecCriteres(criteres);

        return tiersRepository.findAll(spec);
    }

    // Méthode alternative plus simple
    public List<Tiers> rechercherTiersSimplement(Map<String, Object> criteres) {

        if (criteres.containsKey("nom") && criteres.containsKey("prenom")) {
            String nom = criteres.get("nom") != null ? criteres.get("nom").toString() : "";
            String prenom = criteres.get("prenom") != null ? criteres.get("prenom").toString() : "";

            // Recherche exacte
            return tiersRepository.findByNomAndPrenom(nom, prenom);

            // Ou recherche partielle (si vous ajoutez cette méthode au repository)
            // return tiersRepository.findByNomContainingAndPrenomContaining(nom, prenom);
        }

        if (criteres.containsKey("numeroIdentifiantFiscal")) {
            return tiersRepository.findByNumeroIdentifiantFiscal(
                    criteres.get("numeroIdentifiantFiscal").toString()
            );
        }

        // Recherche par nom seul
        if (criteres.containsKey("nom")) {
            String nom = criteres.get("nom").toString();
            return tiersRepository.findByNomContainingIgnoreCase(nom);
        }

        // Recherche par prénom seul
        if (criteres.containsKey("prenom")) {
            String prenom = criteres.get("prenom").toString();
            return tiersRepository.findByPrenomContainingIgnoreCase(prenom);
        }

        return tiersRepository.findAll();
    }
}