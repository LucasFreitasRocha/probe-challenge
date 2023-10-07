package com.ta2.probechallenge.planet.repository;

import com.ta2.probechallenge.exception.CustomExceptionService;
import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.entity.PlanetEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
    public PlanetDomain save(PlanetDomain domain) {
        return PlanetDomain.from(repositorySql.save(PlanetEntity.from(domain)));
    }

    @Override
    public void delete(UUID id) {
        repositorySql.delete(PlanetEntity.from(find(id)));
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
