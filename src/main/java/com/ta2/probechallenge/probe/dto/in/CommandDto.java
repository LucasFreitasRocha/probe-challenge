package com.ta2.probechallenge.probe.dto.in;

import io.swagger.annotations.ApiModelProperty;


public record CommandDto(
        @ApiModelProperty(value = "Command", example = "LMLMLMLMM")
        String command
) {
}
