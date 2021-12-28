package com.example.rest_example.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OperationTypeValidator.class)
@Target( {ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationType {
    String message() default "Invalid operation type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}