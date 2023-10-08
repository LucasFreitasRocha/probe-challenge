package com.ta2.probechallenge.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor

public class CustomException extends RuntimeException {


    private final HttpStatus status;
    private final List<ErroInformation> erroInformations;

    public CustomException(String message, HttpStatus status, List<ErroInformation> erroInformations) {
        super(message);
        this.status = status;
        this.erroInformations = erroInformations;
    }

    public static CustomException buildBy(CodeExceptionEnum codeExceptionEnum, String resource, HttpStatus status) {
        return CustomException.builder()
                .status(status)
                .erroInformations(List.of(new ErroInformation(codeExceptionEnum.code, codeExceptionEnum.message.formatted(resource))))
                .build();
    }
}
