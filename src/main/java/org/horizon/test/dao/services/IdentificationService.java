package org.horizon.test.dao.services;

import org.horizon.test.dao.entities.Identification;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IdentificationService {

    Page<Identification> getAllIdentifications(String raisonSociale, int page, int size);

    Identification getIdentification(Long idIdentification);

    Identification addIdentification( Identification identification);

    Identification updateIdentification(Identification identification);

}
