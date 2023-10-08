package com.ta2.probechallenge.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {




    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomError> handleCustomException(CustomException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(CustomError.builder()
                .path(request.getRequestURI())
                .id(UUID.randomUUID())
                .date(LocalDateTime.now())
                .errors(ex.getErroInformations())
                .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CustomError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErroInformation> erroInformation = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            erroInformation.add(new ErroInformation(error.getCode(), error.getDefaultMessage()));
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomError.builder()
                .errors(erroInformation)
                .id(UUID.randomUUID())
                .date(LocalDateTime.now())
                .path(request.getRequestURI())
                .build());
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<CustomError> handleJsonParseException(Exception ex, HttpServletRequest request) {
        List<ErroInformation> erroInformation = new ArrayList<>();
        erroInformation.add(new ErroInformation("json.parse", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CustomError.builder()
                        .errors(erroInformation)
                        .id(UUID.randomUUID())
                        .date(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomError> handleGenericException(Exception ex, HttpServletRequest request) {
        List<ErroInformation> erroInformation = new ArrayList<>();
        erroInformation.add(new ErroInformation("INTERNAL_SERVER_ERROR", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                CustomError.builder()
                        .errors(erroInformation)
                        .id(UUID.randomUUID())
                        .date(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build()
        );
    }


}
