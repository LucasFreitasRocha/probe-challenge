package com.ta2.probechallenge.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CodeExceptionEnum {

    CREATION_UNAVAILABLE(HttpStatus.BAD_REQUEST, "the creation of this resource is currently unavailable.");

    public final HttpStatus code;
    public final String message;

}
