package com.ta2.probechallenge.planet.repository;

import com.ta2.probechallenge.exception.CodeExceptionEnum;
import com.ta2.probechallenge.exception.CustomException;
import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.entity.PlanetEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PlanetRepositoryAdapterImp implements PlanetRespositoryAdapter {

    private final PlanetRepositorySql repositorySql;

    @Override
    public PlanetDomain find(UUID id) {
        return PlanetDomain.from(
                repositorySql.
                        findById(id)
                        .orElseThrow(
                                () -> {
                                    throw CustomException.buildBy(CodeExceptionEnum.NOT_FOUND, "planet", HttpStatus.NOT_FOUND);
                                }));
    }

    @Override
    public PlanetDomain save(PlanetDomain domain) {
        return PlanetDomain.from(repositorySql.save(PlanetEntity.from(domain)));
    }

    @Override
    public void delete(PlanetDomain domain) {
        repositorySql.delete(PlanetEntity.from(domain));
    }

    @Override
    public List<PlanetDomain> findAll() {
        return repositorySql.findAll().stream().map(PlanetDomain::from).toList();
    }

    @Override
    public Optional<PlanetDomain> findByName(String name) {
        Optional<PlanetEntity> optionalEntity = repositorySql.findByName(name.toUpperCase());
        return (optionalEntity.isEmpty()) ? Optional.empty() : optionalEntity.map(PlanetDomain::from);
    }
}
