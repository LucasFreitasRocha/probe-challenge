package com.ta2.probechallenge.probe.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProbeDto(
        @NotNull
        @NotBlank(message = "O nome não pode está em branco")
        String name
) {
}
