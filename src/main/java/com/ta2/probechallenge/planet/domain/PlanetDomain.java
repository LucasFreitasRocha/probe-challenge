package com.ta2.probechallenge.planet.domain;

import com.ta2.probechallenge.planet.entity.PlanetEntity;
import com.ta2.probechallenge.probe.domain.ProbeDomain;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Builder
@AllArgsConstructor
@Data
public class PlanetDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Builder.Default
    private List<ProbeDomain> probes = new ArrayList<>();
    private Integer area;


    public static PlanetDomain from(PlanetEntity entity) {
        return PlanetDomain
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .probes((Objects.isNull(entity.getProbes()))
                        ? new ArrayList<>() : entity.getProbes().stream().map(ProbeDomain::from).toList())
                .area(entity.getArea())
                .build();
    }

}
