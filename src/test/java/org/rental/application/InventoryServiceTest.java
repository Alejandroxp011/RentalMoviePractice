package org.rental.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rental.domain.entities.Inventory;
import org.rental.domain.exceptions.EntityNotFoundException;
import org.rental.domain.exceptions.InvalidArgumentException;
import org.rental.infraestructure.persistence.InventoryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsMovieAvailable() {
        Inventory inventory = new Inventory(1, 10);
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.of(inventory));

        boolean isAvailable = inventoryService.isMovieAvailable(1, 5);

        assertTrue(isAvailable);
    }

    @Test
    void testIsMovieAvailableNotEnoughCopies() {
        Inventory inventory = new Inventory(1, 3);
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.of(inventory));

        boolean isAvailable = inventoryService.isMovieAvailable(1, 5);

        assertFalse(isAvailable);
    }

    @Test
    void testIsMovieAvailableNotFound() {
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.isMovieAvailable(1, 5));
    }

    @Test
    void testReduceInventory() {
        Inventory inventory = new Inventory(1, 10);
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.of(inventory));

        inventoryService.reduceInventory(1, 5);

        assertEquals(5, inventory.getAvailableCopies());
        verify(inventoryRepository, times(1)).save(inventory);
    }

    @Test
    void testReduceInventoryNotEnoughCopies() {
        Inventory inventory = new Inventory(1, 3);
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.of(inventory));

        assertThrows(InvalidArgumentException.class, () -> inventoryService.reduceInventory(1, 5));
    }

    @Test
    void testReduceInventoryNotFound() {
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.reduceInventory(1, 5));
    }

    @Test
    void testIncreaseInventory() {
        Inventory inventory = new Inventory(1, 10);
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.of(inventory));

        inventoryService.increaseInventory(1, 5);

        assertEquals(15, inventory.getAvailableCopies());
        verify(inventoryRepository, times(1)).save(inventory);
    }

    @Test
    void testIncreaseInventoryNotFound() {
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.increaseInventory(1, 5));
    }

    @Test
    void testAddNewMovieToInventory() {
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.empty());

        inventoryService.addNewMovieToInventory(1, 10);

        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testAddNewMovieToInventoryAlreadyExists() {
        Inventory inventory = new Inventory(1, 10);
        when(inventoryRepository.findByMovieId(1)).thenReturn(Optional.of(inventory));

        assertThrows(InvalidArgumentException.class, () -> inventoryService.addNewMovieToInventory(1, 10));
    }
}