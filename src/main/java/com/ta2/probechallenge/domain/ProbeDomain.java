package com.ta2.probechallenge.domain;

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
public class ProbeDomain {

    private Long id;
    private String name;
    private Integer x;
    private Integer y;
    private String position;
}
