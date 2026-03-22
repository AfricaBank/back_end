package org.africa.bank.specification;

import org.africa.bank.entity.Tiers;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TiersSpecification {

    public static Specification<Tiers> avecCriteres(Map<String, Object> criteres) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteres.containsKey("nom")) {
                predicates.add(cb.like(
                        cb.lower(root.get("nom")),
                        "%" + criteres.get("nom").toString().toLowerCase() + "%"
                ));
            }
            if (criteres.containsKey("prenom")) {
                predicates.add(cb.like(
                        cb.lower(root.get("prenom")),
                        "%" + criteres.get("prenom").toString().toLowerCase() + "%"
                ));
            }
            if (criteres.containsKey("numeroIdentifiantFiscal")) {
                predicates.add(cb.equal(
                        root.get("numeroIdentifiantFiscal"),
                        criteres.get("numeroIdentifiantFiscal")
                ));
            }
            // AJOUT : recherche par idNational (siège/racine)
            if (criteres.containsKey("idNational")) {
                predicates.add(cb.like(
                        root.get("numeroImmatriculation"),
                        "%" + criteres.get("idNational") + "%"
                ));
            }
            if (criteres.containsKey("email")) {
                predicates.add(cb.like(
                        cb.lower(root.get("email")),
                        "%" + criteres.get("email").toString().toLowerCase() + "%"
                ));
            }
            if (criteres.containsKey("mobile")) {
                predicates.add(cb.like(
                        root.get("mobile"),
                        "%" + criteres.get("mobile") + "%"
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}