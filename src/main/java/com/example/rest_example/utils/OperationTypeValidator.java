package com.example.rest_example.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.NoSuchElementException;

public class OperationTypeValidator implements ConstraintValidator<OperationType, String> {

    @Override
    public boolean isValid(String operationType, ConstraintValidatorContext constraintValidatorContext) {
        try {
            com.example.rest_example.model.jpa.OperationType.getTypeByName(operationType);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
