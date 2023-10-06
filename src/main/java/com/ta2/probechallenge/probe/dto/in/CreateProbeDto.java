package com.ta2.probechallenge.probe.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ta2.probechallenge.validation.UuidValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateProbeDto(
        @Schema(name = "planet_id", example = "40b3ebd5-2475-42ec-8e50-38384ae7d80b")
        @JsonProperty("planet_id")
        @NotNull(message = "the id_planet cannot be null")
        @UuidValidation
        String idPlanet,
        @NotNull(message = "The name cannot be null")
        @NotBlank(message = "the name cannot be blank")
        @Schema(name = "name", example = "mars rover")
        String name
) {
}
