package com.ta2.probechallenge.planet.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.probe.dto.out.ListProbeDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record PlanetDto(
        UUID id,
        String name,
        @JsonProperty("max_probes_in")
        Integer maxProbesIn,
        Integer area,

        List<ListProbeDto> probes,
        @JsonProperty("create_at")
        LocalDateTime createAt,
        @JsonProperty("update_at")
        LocalDateTime updateAt

) {

    public static PlanetDto from(PlanetDomain domain) {
        return PlanetDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .area(domain.getArea())
                .maxProbesIn(domain.getMaxProbesIn())
                .createAt(domain.getCreateAt())
                .updateAt(domain.getUpdateAt())
                .probes(domain.getProbes().stream().map(ListProbeDto::from).toList())
                .build();
    }
}
