package org.rental.domain.exceptions;

public class SqlOperationException extends RuntimeException {
    public SqlOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
