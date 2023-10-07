package com.ta2.probechallenge.planet.entity;

import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.probe.entity.ProbeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "planet")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String name;
    @Builder.Default
    @OneToMany( mappedBy="planet")
    private List<ProbeEntity> probes = new ArrayList<>();
    private Integer area;
    private Integer maxProbesIn;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;



    public static PlanetEntity from(PlanetDomain domain) {
        return PlanetEntity
                .builder()
                .id(domain.getId())
                .name(domain.getName().toUpperCase())
                .maxProbesIn(domain.getMaxProbesIn())
                .createAt(domain.getCreateAt())
                .updateAt(domain.getUpdateAt())
                .probes((Objects.isNull(domain.getProbes()))
                        ? new ArrayList<>() : domain.getProbes().stream().map(ProbeEntity::from).toList())
                .area(domain.getArea())
                .build();
    }
}
