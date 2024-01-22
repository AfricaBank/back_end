package org.horizon.test.dao.services.Impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.EER;
import org.horizon.test.dao.repositories.EERRepository;
import org.horizon.test.dao.services.EERService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EERServiceImpl implements EERService {

    private EERRepository eerRepository;
    @Override
    public Page<EER> getAllEERs(String motifEER, int page, int size) {
        return eerRepository.findEERByMotifEERContains(motifEER, PageRequest.of(page, size));
    }

    @Override
    public EER getEER(Long idEER) {
        return eerRepository.findById(idEER).orElse(null);
    }

    @Override
    public EER addEER(EER eer) {
        return eerRepository.save(eer);
    }

    @Override
    public EER updateEER(EER eer) {
        return eerRepository.save(eer);
    }
}
