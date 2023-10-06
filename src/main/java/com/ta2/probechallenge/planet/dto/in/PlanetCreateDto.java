package com.ta2.probechallenge.planet.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlanetCreateDto(
        @NotNull
        @NotBlank(message = "The name cannot be blank")
        @Schema(name = "name", example = "mars")
        String name,
        @Max(value = 5, message = "Max probes in a planet is five")
        @Min(value = 1, message = "Min probes in a planet is one")
        Integer maxProbesIn,
        @Min(value = 5, message = "the minimum of area in a  planet is 5")
        Integer area
) {
}
