package com.example.rest_example.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SocksColorValidator.class)
@Target( {ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SocksColor {
    String message() default "Invalid socks color";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}