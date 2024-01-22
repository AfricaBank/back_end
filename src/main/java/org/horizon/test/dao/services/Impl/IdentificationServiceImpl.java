package org.horizon.test.dao.services.Impl;

import lombok.AllArgsConstructor;
import org.horizon.test.dao.entities.Identification;
import org.horizon.test.dao.repositories.IdentificationRepository;
import org.horizon.test.dao.services.IdentificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IdentificationServiceImpl implements IdentificationService {

    private IdentificationRepository identificationRepository;

    @Override
    public Page<Identification> getAllIdentifications(String raisonSociale, int page, int size) {
        return identificationRepository.findIdentificationsByRaisonSocialeContains(raisonSociale, PageRequest.of(page, size));
    }

    @Override
    public Identification getIdentification(Long idIdentification) {
        return identificationRepository.findById(idIdentification).orElse(null);
    }

    @Override
    public Identification addIdentification(Identification identification) {
        return identificationRepository.save(identification);
    }

    @Override
    public Identification updateIdentification(Identification identification) {
        return identificationRepository.save(identification);
    }
}
