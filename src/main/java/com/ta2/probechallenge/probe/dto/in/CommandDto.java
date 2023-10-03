package com.ta2.probechallenge.probe.dto.in;



import io.swagger.v3.oas.annotations.media.Schema;

public record CommandDto(
        @Schema( example = "LMLMLMLMM")
        String command
) {
}
