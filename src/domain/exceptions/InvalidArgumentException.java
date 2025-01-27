package movies.src.domain.exceptions;

public class InvalidArgumentException extends IllegalArgumentException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}