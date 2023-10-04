package com.ta2.probechallenge.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExeceptionHandler {



    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomError> handleCustomException(CustomException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(CustomError.builder()
                .path(request.getRequestURI())
                .id(UUID.randomUUID())
                .date(LocalDateTime.now())
                .erroInformations(ex.getErroInformations())
                .build());
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomError> handleGenericExecption(Exception ex, HttpServletRequest request) {
        List<ErroInformation> erroInformation = new ArrayList<>();
        erroInformation.add(new ErroInformation("INTERNAL_SERVER_ERROR", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                CustomError.builder()
                        .erroInformations(erroInformation)
                        .id(UUID.randomUUID())
                        .date(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build()
        );
    }


}
