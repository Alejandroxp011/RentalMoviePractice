package org.rental.application;

import org.rental.application.validators.Validator;
import org.rental.domain.entities.Inventory;
import org.rental.domain.exceptions.EntityNotFoundException;
import org.rental.domain.exceptions.InvalidArgumentException;
import org.rental.infraestructure.persistence.InventoryRepository;

public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean isMovieAvailable(int movieId, int requiredQuantity) {
        Validator.validatePositiveNumber(movieId, "Movie ID");
        Inventory inventory = inventoryRepository.findByMovieId(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found for movie ID: " + movieId));
        return inventory.getAvailableCopies() >= requiredQuantity;
    }

    public void reduceInventory(int movieId, int quantity) {
        Validator.validatePositiveNumber(movieId, "Movie ID");
        Inventory inventory = inventoryRepository.findByMovieId(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found for movie ID: " + movieId));
        if (inventory.getAvailableCopies() < quantity) {
            throw new InvalidArgumentException("Not enough copies available for movie ID: " + movieId);
        }
        inventory.setAvailableCopies(inventory.getAvailableCopies() - quantity);
        inventoryRepository.save(inventory);
    }

    public void increaseInventory(int movieId, int quantity) {
        Validator.validatePositiveNumber(movieId, "Movie ID");
        Inventory inventory = inventoryRepository.findByMovieId(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found for movie ID: " + movieId));
        inventory.setAvailableCopies(inventory.getAvailableCopies() + quantity);
        inventoryRepository.save(inventory);
    }

    public void addNewMovieToInventory(int movieId, int initialQuantity) {
        Validator.validatePositiveNumber(movieId, "Movie ID");
        if (inventoryRepository.findByMovieId(movieId).isPresent()) {
            throw new InvalidArgumentException("Inventory already exists for movie ID: " + movieId);
        }
        Inventory inventory = new Inventory(movieId, initialQuantity);
        inventoryRepository.save(inventory);
    }
}