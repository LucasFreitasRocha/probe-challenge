package com.ta2.probechallenge.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {}
)
@Pattern(regexp="[mlrMLR]", message = "Send a valid command")
public @interface CommandValidation {
    String message() default "Send a valid command";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}