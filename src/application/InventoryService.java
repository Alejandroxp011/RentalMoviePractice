package movies.src.application;

import movies.src.domain.entities.Inventory;
import movies.src.domain.exceptions.InvalidArgumentException;
import movies.src.domain.exceptions.InventoryUnavailableException;

public class InventoryService {
    private final MovieInventoryRepository inventoryRepository;

    public InventoryService(MovieInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean isMovieAvailable(int movieId, int requiredQuantity) {
        if (requiredQuantity <= 0) {
            throw new InvalidArgumentException("Required quantity must be greater than zero.");
        }
        Inventory inventory = inventoryRepository.findByMovieId(movieId)
                .orElseThrow(() -> new InventoryUnavailableException("Inventory not found for movie ID: " + movieId));
        return inventory.getAvailableCopies() >= requiredQuantity;
    }

    public void reduceInventory(int movieId, int quantity) {
        if (quantity <= 0) {
            throw new InvalidArgumentException("Quantity must be greater than zero.");
        }
        Inventory inventory = inventoryRepository.findByMovieId(movieId)
                .orElseThrow(() -> new InventoryUnavailableException("Inventory not found for movie ID: " + movieId));
        if (inventory.getAvailableCopies() < quantity) {
            throw new InventoryUnavailableException("Not enough copies available for movie ID: " + movieId);
        }
        inventory.setAvailableCopies(inventory.getAvailableCopies() - quantity);
        inventoryRepository.save(inventory);
    }

    public void addInventory(int movieId, int quantity) {
        if (quantity <= 0) {
            throw new InvalidArgumentException("Quantity must be greater than zero.");
        }
        Inventory inventory = inventoryRepository.findByMovieId(movieId)
                .orElse(new Inventory(movieId, 0));
        inventory.setAvailableCopies(inventory.getAvailableCopies() + quantity);
        inventoryRepository.save(inventory);
    }
}
