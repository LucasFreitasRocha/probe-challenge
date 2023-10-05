package com.ta2.probechallenge.probe.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateProbeDto(
        @Schema(name = "planet_id", example = "40b3ebd5-2475-42ec-8e50-38384ae7d80b")
        @NotNull
        @JsonProperty("planet_id")
        UUID idPlanet,
        @NotNull
        @NotBlank(message = "The name cannot be blank")
        @Schema(name = "name", example = "mars rover")
        String name
) {
}
