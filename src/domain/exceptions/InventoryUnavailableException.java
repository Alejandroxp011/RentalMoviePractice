package movies.src.domain.exceptions;

public class InventoryUnavailableException extends RuntimeException {
    public InventoryUnavailableException(String message) {
        super(message);
    }
}
