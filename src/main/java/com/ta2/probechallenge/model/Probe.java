package com.ta2.probechallenge.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Probe {
    @Id
    private Long id;
    private String name;
    private Integer x;
    private Integer y;
    private String position;
}
