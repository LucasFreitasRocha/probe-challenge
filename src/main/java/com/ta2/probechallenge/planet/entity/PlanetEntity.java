package com.ta2.probechallenge.planet.entity;

import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.probe.entity.ProbeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "planet")
@Builder
@AllArgsConstructor
@Data
public class PlanetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    @Builder.Default
    @OneToMany
    private List<ProbeEntity> probes = new ArrayList<>();

    private Integer area;


    public static PlanetEntity from(PlanetDomain domain) {
        return PlanetEntity
                .builder()
                .id(domain.getId())
                .name(domain.getName())
                .probes((Objects.isNull(domain.getProbes()))
                        ? new ArrayList<>() : domain.getProbes().stream().map(ProbeEntity::from).toList())
                .area(domain.getArea())
                .build();
    }
}
