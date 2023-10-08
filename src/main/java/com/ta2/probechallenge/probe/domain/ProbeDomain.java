package com.ta2.probechallenge.probe.domain;

import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.probe.entity.ProbeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProbeDomain {

    private Long id;
    private String name;
    private String code;
    private Integer x;
    private Integer y;
    private String position;
    private PlanetDomain planet;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


    public static ProbeDomain from(ProbeEntity probeEntity) {
        PlanetDomain planetDomain = new PlanetDomain();
        planetDomain.setId(probeEntity.getPlanet().getId());
        planetDomain.setName(probeEntity.getPlanet().getName());
        planetDomain.setArea(probeEntity.getPlanet().getArea());
        planetDomain.setMaxProbesIn(probeEntity.getPlanet().getMaxProbesIn());
        return ProbeDomain
                .builder()
                .id(probeEntity.getId())
                .name(probeEntity.getName())
                .code(probeEntity.getCode())
                .y(probeEntity.getY())
                .x(probeEntity.getX())
                .planet(planetDomain)
                .position(probeEntity.getPosition())
                .createAt(probeEntity.getCreateAt())
                .updateAt(probeEntity.getUpdateAt())
                .build();
    }
}
