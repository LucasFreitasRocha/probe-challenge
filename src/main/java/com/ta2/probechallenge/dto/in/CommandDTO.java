package com.ta2.probechallenge.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommandDTO {
    @ApiModelProperty(value = "Command", example = "LMLMLMLMM")
    private String command;
}
