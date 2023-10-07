package com.ta2.probechallenge.planet.usecase;

import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.dto.in.PlanetCreateDto;
import com.ta2.probechallenge.planet.repository.PlanetRespositoryAdapter;
import com.ta2.probechallenge.planet.validation.PlanetValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PlanetUseCase {

    @Autowired
    private PlanetRespositoryAdapter respositoryAdapter;

    @Autowired
    private PlanetValidation validation;

    public PlanetDomain create(PlanetDomain domain) {
        validation.validationUpdateUniqueName(domain.getName(), null);
        return respositoryAdapter.save(domain);
    }

    public PlanetDomain find(UUID id) {
        return respositoryAdapter.find(id);
    }

    public PlanetDomain update(UUID id, PlanetCreateDto planetCreateDto) {
        validation.validationUpdateUniqueName(planetCreateDto.name(), id);
        PlanetDomain  domain = this.find(id);
        domain.setArea(planetCreateDto.area());
        domain.setName(planetCreateDto.name());
        domain.setMaxProbesIn(planetCreateDto.maxProbesIn());
        return respositoryAdapter.save(domain);
    }

    public void delete(UUID id) {
        respositoryAdapter.delete(id);
    }

    public List<PlanetDomain> findAll() {
        return respositoryAdapter.findAll();
    }
}
