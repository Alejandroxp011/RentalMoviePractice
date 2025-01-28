package org.rental.application.validators;

import org.rental.domain.exceptions.InvalidArgumentException;

import java.lang.reflect.Field;

public class Validator {

    public static void validatePositiveNumber(int number, String fieldName) {
        if (number <= 0) {
            throw new InvalidArgumentException(fieldName + " must be greater than zero.");
        }
    }

    public static void validateNotNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new InvalidArgumentException(fieldName + " cannot be null.");
        }
    }

    public static void validateObjectPropertiesNotNull(Object obj, String fieldName) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(obj) == null) {
                    throw new InvalidArgumentException(fieldName + " cannot have null properties.");
                }
            } catch (IllegalAccessException e) {
                throw new InvalidArgumentException("Error while validating object properties.");
            }
        }
    }

    public static void validateNotEmpty(String str, String fieldName) {
        if (str == null || str.trim().isEmpty()) {
            throw new InvalidArgumentException(fieldName + " cannot be empty.");
        }
    }
}