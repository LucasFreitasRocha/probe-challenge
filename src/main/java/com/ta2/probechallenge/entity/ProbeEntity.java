package com.ta2.probechallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "probe")
public class ProbeEntity {
    @Id
    private Long id;
    private String name;
    private Integer x;
    private Integer y;
    private String position;

    public static ProbeEntity from(com.ta2.probechallenge.domain.ProbeDomain probeDomain){
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
