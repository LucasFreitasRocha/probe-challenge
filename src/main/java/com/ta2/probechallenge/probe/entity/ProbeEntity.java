package com.ta2.probechallenge.probe.entity;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Data
@AllArgsConstructor
@Builder
@Entity(name = "probe")
public class ProbeEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    private String name;
    private Integer x;
    private Integer y;
    private String position;

    public static ProbeEntity from(ProbeDomain probeDomain) {
        return ProbeEntity
                .builder()
                .id(probeDomain.getId())
                .name(probeDomain.getName())
                .y(probeDomain.getY())
                .x(probeDomain.getX())
                .position(probeDomain.getPosition())
                .build();
    }
}
