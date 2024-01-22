package org.horizon.test.dao.services;

import org.horizon.test.dao.entities.EER;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface EERService {

    Page<EER> getAllEERs(String motifEER, int page, int size);

    EER getEER(Long idEER);

    EER addEER( EER eer);

    EER updateEER(EER eer);

}
