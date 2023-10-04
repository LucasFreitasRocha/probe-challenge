package com.ta2.probechallenge.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CustomError {

    private UUID id;
    private List<ErroInformation> erroInformations;
    private String path;
    private LocalDateTime date;
}
