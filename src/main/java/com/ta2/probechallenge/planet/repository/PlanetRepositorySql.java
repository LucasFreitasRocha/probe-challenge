package com.ta2.probechallenge.planet.repository;

import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.entity.PlanetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanetRepositorySql extends JpaRepository<PlanetEntity, UUID> {
    Optional<PlanetEntity> findByName(String upperCase);
}
