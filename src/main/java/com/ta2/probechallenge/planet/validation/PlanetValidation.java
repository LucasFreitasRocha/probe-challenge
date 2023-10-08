package com.ta2.probechallenge.planet.validation;


import com.ta2.probechallenge.planet.domain.PlanetDomain;

import java.util.UUID;

public interface PlanetValidation {

    public void validationUpdateUniqueName(String name, UUID id);


    PlanetDomain canDelete(UUID id);
}
