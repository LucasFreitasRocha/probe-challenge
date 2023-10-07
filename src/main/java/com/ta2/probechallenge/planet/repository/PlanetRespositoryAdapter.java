package com.ta2.probechallenge.planet.repository;

import com.ta2.probechallenge.planet.domain.PlanetDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanetRespositoryAdapter {

    PlanetDomain find(UUID id);

    PlanetDomain save(PlanetDomain planetDomain);

    void delete(UUID id);

    List<PlanetDomain> findAll();

    Optional<PlanetDomain> findByName(String name);
}
