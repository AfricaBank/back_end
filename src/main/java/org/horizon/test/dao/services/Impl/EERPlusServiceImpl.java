package org.horizon.test.dao.services.Impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.EERPlus;
import org.horizon.test.dao.repositories.EERPlusRepository;
import org.horizon.test.dao.services.EERPlusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EERPlusServiceImpl implements EERPlusService {

    private EERPlusRepository eerPlusRepository;
    @Override
    public Page<EERPlus> getAllEERPluss(String motifEERPlus, int page, int size) {
        return eerPlusRepository.findEERPlusByMotifEERContains(motifEERPlus, PageRequest.of(page, size));
    }

    @Override
    public EERPlus getEERPlus(Long idEERPlus) {
        return eerPlusRepository.findById(idEERPlus).orElse(null);
    }

    @Override
    public EERPlus addEERPlus(EERPlus eer) {
        return eerPlusRepository.save(eer);
    }

    @Override
    public EERPlus updateEERPlus(EERPlus eer) {
        return eerPlusRepository.save(eer);
    }
}
