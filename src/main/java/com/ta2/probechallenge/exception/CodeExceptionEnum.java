package com.ta2.probechallenge.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CodeExceptionEnum {

    CREATION_OR_UPDATE_UNAVAILABLE("invalid.create.or.update", "the handling of this resource is currently unavailable. Because the %s already exist"),
    NOT_FOUND("notfound.resource", "this %s not found"),
    DELETE_UNAVAILABLE("invalid.delete", "the delete of this %s is currently unavailabe."),
    MAX_PROBES_ON_PLANET("max.probes.on", "This planet has reached the maximum allowed value"),
    INVALID_POSITION("invalid.position", "this position is invalid, because %s"),
    INVALID_COMMAND("invalid.command", "olny this command is valid: M - move; L - turn left ; R - turn right ");

    public final String code;
    public final String message;

}
