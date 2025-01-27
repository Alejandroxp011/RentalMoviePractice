package movies.src.application;

import java.time.LocalDate;

import movies.src.domain.entities.Rental;
import movies.src.domain.entities.RentalMovie;
import movies.src.domain.exceptions.EntityNotFoundException;
import movies.src.domain.exceptions.InvalidArgumentException;
import movies.src.domain.exceptions.InvalidRentalOperationException;
import movies.src.infraestructure.persistence.GenericRepository;

public class RentalService {
    private final GenericRepository<Rental, Integer> rentalRepository;
    private final InventoryService inventoryService;

    public RentalService(GenericRepository<Rental, Integer> rentalRepository, InventoryService inventoryService) {
        this.rentalRepository = rentalRepository;
        this.inventoryService = inventoryService;
    }

    public void createRental(Rental rental) {
        if (rental.getRentalMovies() == null || rental.getRentalMovies().isEmpty()) {
            throw new InvalidArgumentException("A rental must include at least one movie.");
        }

        for (RentalMovie rentalMovie : rental.getRentalMovies()) {
            if (!inventoryService.isMovieAvailable(rentalMovie.getMovieId(), rentalMovie.getQuantity())) {
                throw new InvalidRentalOperationException(
                        "Not enough inventory for movie ID: " + rentalMovie.getMovieId()
                );
            }
        }

        for (RentalMovie rentalMovie : rental.getRentalMovies()) {
            inventoryService.reduceInventory(rentalMovie.getMovieId(), rentalMovie.getQuantity());
        }

        rentalRepository.save(rental);
    }

    public void returnRental(int rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with ID: " + rentalId));

        if (rental.getReturnDate() != null) {
            throw new InvalidRentalOperationException("Rental with ID " + rentalId + " has already been returned.");
        }

        rental.setReturnDate(LocalDate.now());
        rentalRepository.update(rental);

        for (RentalMovie rentalMovie : rental.getRentalMovies()) {
            inventoryService.addInventory(rentalMovie.getMovieId(), rentalMovie.getQuantity());
        }
    }
}
