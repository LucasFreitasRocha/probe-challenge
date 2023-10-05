package com.ta2.probechallenge.probe.dto.out;

import com.ta2.probechallenge.probe.domain.ProbeDomain;

public record ListProbeDto(
        Long id,
        String name,
        String code
) {

    public static ListProbeDto from(ProbeDomain domain){
        return new ListProbeDto(domain.getId(), domain.getName(), domain.getCode());
    }
}
