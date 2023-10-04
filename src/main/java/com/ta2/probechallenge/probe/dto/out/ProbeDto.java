package com.ta2.probechallenge.probe.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.entity.ProbeEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProbeDto(
         Long id,
         String name,
         String code,
         Integer x,
         Integer y,
         String position,
         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
         @JsonProperty("create_at")
         LocalDateTime createAt,
         @JsonProperty("update_at")
         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
         LocalDateTime updateAt
) {

    public static ProbeDto from(ProbeDomain probeDomain) {
        return ProbeDto
                .builder()
                .id(probeDomain.getId())
                .name(probeDomain.getName())
                .code(probeDomain.getCode())
                .y(probeDomain.getY())
                .x(probeDomain.getX())
                .position(probeDomain.getPosition())
                .createAt(probeDomain.getCreateAt())
                .updateAt(probeDomain.getUpdateAt())
                .build();
    }
}
