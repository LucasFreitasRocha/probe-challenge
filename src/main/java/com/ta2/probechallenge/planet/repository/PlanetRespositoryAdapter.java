package com.ta2.probechallenge.planet.repository;

import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.dto.in.PlanetCreateDto;

import java.util.UUID;

public interface PlanetRespositoryAdapter {

    PlanetDomain find(UUID id);

    PlanetDomain create(PlanetDomain planetDomain);
}
