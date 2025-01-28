package org.rental.domain.exceptions;

public class InvalidRentalOperationException extends RuntimeException {
    public InvalidRentalOperationException(String message) {
        super(message);
    }
}