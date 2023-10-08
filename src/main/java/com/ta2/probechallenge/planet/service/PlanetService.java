package com.ta2.probechallenge.planet.service;

import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.dto.in.PlanetCreateDto;
import com.ta2.probechallenge.planet.dto.out.ListPlanetDto;
import com.ta2.probechallenge.planet.dto.out.PlanetDto;
import com.ta2.probechallenge.planet.usecase.PlanetUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlanetService {

    @Autowired
    private PlanetUseCase useCase;


    public PlanetDto create(PlanetCreateDto planetCreateDto) {
        return PlanetDto.from(useCase.create(PlanetDomain.builder()
                .name(planetCreateDto.name())
                .maxProbesIn(planetCreateDto.maxProbesIn())
                .area(planetCreateDto.area())
                .build()));
    }

    public PlanetDto find(UUID id){
        return PlanetDto.from(useCase.find(id));
    }

    public PlanetDto update(UUID id, PlanetCreateDto planetCreateDto){
        return  PlanetDto.from(useCase.update(id,planetCreateDto));
    }


    public void delte(UUID id){
        useCase.delete(id);
    }

    public List<ListPlanetDto> findAll(){
        return useCase.findAll().stream().map(ListPlanetDto::from).toList();
    }


}
