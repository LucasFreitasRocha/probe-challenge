package com.ta2.probechallenge.probe.domain;

import com.ta2.probechallenge.probe.entity.ProbeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProbeDomain {

    private Long id;
    private String name;
    private Integer x;
    private Integer y;
    private String position;

    public static ProbeDomain from(ProbeEntity probeEntity) {
        return ProbeDomain
                .builder()
                .id(probeEntity.getId())
                .name(probeEntity.getName())
                .y(probeEntity.getY())
                .x(probeEntity.getX())
                .position(probeEntity.getPosition())
                .build();
    }
}
