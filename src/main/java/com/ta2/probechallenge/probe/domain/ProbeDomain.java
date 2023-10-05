package com.ta2.probechallenge.probe.domain;

import com.ta2.probechallenge.planet.entity.PlanetEntity;
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
    private PlanetEntity planet;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


    public static ProbeDomain from(ProbeEntity probeEntity) {
        return ProbeDomain
                .builder()
                .id(probeEntity.getId())
                .name(probeEntity.getName())
                .code(probeEntity.getCode())
                .y(probeEntity.getY())
                .x(probeEntity.getX())
                .planet(probeEntity.getPlanet())
                .position(probeEntity.getPosition())
                .createAt(probeEntity.getCreateAt())
                .updateAt(probeEntity.getUpdateAt())
                .build();
    }
}
