package com.ta2.probechallenge.planet.validation;


import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.probe.domain.ProbeDomain;

import java.util.UUID;

public interface PlanetValidation {

    void validationUpdateUniqueName(String name, UUID id);

    PlanetDomain canUseThisPlanet(UUID id);

    PlanetDomain canDelete(UUID id);

    void validationPositionOfProbeOn(ProbeDomain probeDomain);
}
