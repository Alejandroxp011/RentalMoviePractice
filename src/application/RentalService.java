package movies.src.application;

import java.time.LocalDate;

import movies.src.domain.entities.Rental;
import movies.src.domain.entities.RentalMovie;
import movies.src.domain.exceptions.EntityNotFoundException;
import movies.src.domain.exceptions.InvalidArgumentException;
import movies.src.domain.exceptions.InvalidRentalOperationException;
import movies.src.infraestructure.persistence.GenericRepository;
import movies.src.infraestructure.persistence.RentalRepository;

public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void createRental(Rental rental) {
        if (rental == null || rental.getRentalMovies().isEmpty()) {
            throw new InvalidArgumentException("Invalid rental data.");
        }
        rentalRepository.save(rental);
    }

    public void returnRental(int rentalId) {
        if (rentalId <= 0) {
            throw new InvalidArgumentException("Rental ID must be greater than zero.");
        }
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with ID: " + rentalId));
        rentalRepository.updateReturnDate(rentalId, LocalDate.now());
    }
}