package org.horizon.test.dao.services;

import org.horizon.test.dao.entities.Banque;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BanqueService {

    Page<Banque> getAllBanques(Integer nom, int page, int size);

    Banque getBanque(Long idBanque);

    Banque addBanque( Banque banque);

    Banque updateBanque(Banque banque);

}
