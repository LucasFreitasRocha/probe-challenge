package com.ta2.probechallenge.planet.dto.out;

import com.ta2.probechallenge.planet.domain.PlanetDomain;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ListPlanetDto(
        UUID id,
        String name
) {
    public static ListPlanetDto from(PlanetDomain domain){
        return ListPlanetDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }
}
