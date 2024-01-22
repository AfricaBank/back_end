package org.horizon.test.dao.services;

import org.horizon.test.dao.entities.Coordonnee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface CoordonneeService {


    Page<Coordonnee> getAllCoordonnees(BigInteger telFixe, int page, int size);

    Coordonnee getCoordonnee(Long idCoordonnee);

    Coordonnee addCoordonnee( Coordonnee coordonnee);

    Coordonnee updateCoordonnee(Coordonnee coordonnee);
    
}
