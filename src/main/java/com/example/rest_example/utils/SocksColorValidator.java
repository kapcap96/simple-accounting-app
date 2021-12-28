package com.example.rest_example.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.NoSuchElementException;

public class SocksColorValidator implements ConstraintValidator<SocksColor, String> {

    @Override
    public boolean isValid(String socksColor, ConstraintValidatorContext constraintValidatorContext) {
        try {
            com.example.rest_example.model.jpa.SocksColor.getColorByName(socksColor);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
