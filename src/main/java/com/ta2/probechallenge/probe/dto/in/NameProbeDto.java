package com.ta2.probechallenge.probe.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NameProbeDto(
        @NotNull
        @NotBlank(message = "O nome não pode está em branco")
        @Schema(name = "name", example = "mars rover")
        String name
) {
}
