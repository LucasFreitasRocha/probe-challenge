package com.ta2.probechallenge.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CodeExceptionEnum {

    CREATION_UNAVAILABLE("invalid.create", "the creation of this resource is currently unavailable."),
    INVALID_COMMAND("invalid.command", "olny this command is valid: M - move; L - turn left ; R - turn right ");

    public final String code;
    public final String message;

}
