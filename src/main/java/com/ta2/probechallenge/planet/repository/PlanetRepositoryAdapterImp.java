package com.ta2.probechallenge.planet.repository;

import com.ta2.probechallenge.exception.CustomExceptionService;
import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.dto.in.PlanetCreateDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class PlanetRepositoryAdapterImp implements PlanetRespositoryAdapter {

    private final PlanetRepositorySql repositorySql;
    private final CustomExceptionService customExceptionService;

    @Override
    public PlanetDomain find(UUID id) {
        return PlanetDomain.from(
                repositorySql.
                        findById(id)
                        .orElseThrow(
                                () -> customExceptionService.createNotFound("planet")));
    }

    @Override
    public PlanetDomain create(PlanetDomain PlanetDomain) {
        return null;
    }
}
