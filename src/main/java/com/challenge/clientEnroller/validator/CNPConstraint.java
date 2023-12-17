package com.challenge.clientEnroller.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CNPValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CNPConstraint {
    String message() default "Invalid CNP.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
