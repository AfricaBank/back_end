package org.horizon.test.dao.services;

import org.horizon.test.dao.entities.EERPlus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface EERPlusService {

    Page<EERPlus> getAllEERPluss(int page, int size);

    EERPlus getEERPlus(Long idEERPlus);

    EERPlus addEERPlus( Long idDossier,  EERPlus eerPlus);

    EERPlus updateEERPlus(EERPlus eerPlus);

}
