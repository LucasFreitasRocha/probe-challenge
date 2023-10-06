package com.ta2.probechallenge.probe.dto.in;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommandDto(
        @Schema(example = "LMLMLMLMM")
        @NotNull(message = "the command cannot be null")
        @NotBlank(message = "the command cannot be blank")
        String command
) {
}
