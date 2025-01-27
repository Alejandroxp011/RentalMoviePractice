package org.rental.application;

import org.rental.domain.entities.Inventory;
import org.rental.domain.exceptions.EntityNotFoundException;
import org.rental.domain.exceptions.InvalidArgumentException;
import movies.src.infraestructure.persistence.InventoryRepository;

public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean isMovieAvailable(int movieId, int requiredQuantity) {
        if (requiredQuantity <= 0) {
            throw new InvalidArgumentException("Required quantity must be greater than zero.");
        }
        Inventory inventory = inventoryRepository.findByMovieId(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found for movie ID: " + movieId));
        return inventory.getAvailableCopies() >= requiredQuantity;
    }

    public void reduceInventory(int movieId, int quantity) {
        if (quantity <= 0) {
            throw new InvalidArgumentException("Quantity must be greater than zero.");
        }
        Inventory inventory = inventoryRepository.findByMovieId(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found for movie ID: " + movieId));
        if (inventory.getAvailableCopies() < quantity) {
            throw new InvalidArgumentException("Not enough copies available for movie ID: " + movieId);
        }
        inventory.setAvailableCopies(inventory.getAvailableCopies() - quantity);
        inventoryRepository.save(inventory);
    }

    public void increaseInventory(int movieId, int quantity) {
        if (quantity <= 0) {
            throw new InvalidArgumentException("Quantity must be greater than zero.");
        }
        Inventory inventory = inventoryRepository.findByMovieId(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found for movie ID: " + movieId));
        inventory.setAvailableCopies(inventory.getAvailableCopies() + quantity);
        inventoryRepository.save(inventory);
    }

    public void addNewMovieToInventory(int movieId, int initialQuantity) {
        if (initialQuantity < 0) {
            throw new InvalidArgumentException("Initial quantity cannot be negative.");
        }
        if (inventoryRepository.findByMovieId(movieId).isPresent()) {
            throw new InvalidArgumentException("Inventory already exists for movie ID: " + movieId);
        }
        Inventory inventory = new Inventory(movieId, initialQuantity);
        inventoryRepository.save(inventory);
    }
}