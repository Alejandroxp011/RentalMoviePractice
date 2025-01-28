package org.rental.application;

import java.time.LocalDate;
import java.util.List;

import org.rental.application.validators.Validator;
import org.rental.domain.entities.Customer;
import org.rental.domain.entities.Movie;
import org.rental.domain.entities.Rental;
import org.rental.domain.entities.RentalMovie;
import org.rental.domain.exceptions.EntityNotFoundException;
import org.rental.domain.exceptions.InventoryUnavailableException;
import org.rental.domain.strategies.interfaces.MovieRentalCalculationStrategy;
import org.rental.infraestructure.persistence.CustomerRepository;
import org.rental.infraestructure.persistence.RentalRepository;

public class RentalService {
    private final MovieService movieService;
    private final InventoryService inventoryService;
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;

    public RentalService(MovieService movieService, InventoryService inventoryService,
                         RentalRepository rentalRepository, CustomerRepository customerRepository) {
        this.movieService = movieService;
        this.inventoryService = inventoryService;
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
    }

    public void createRental(Rental rental) {
        Validator.validateNotNull(rental, "Rental");

        Customer customer = customerRepository.findById(rental.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + rental.getCustomerId()));

        double totalCharge = 0;
        int frequentRenterPoints = 0;

        for (RentalMovie rentalMovie : rental.getRentalMovies()) {
            Movie movie = movieService.findById(rentalMovie.getMovieId());

            if (!inventoryService.isMovieAvailable(movie.getId(), rentalMovie.getQuantity())) {
                throw new InventoryUnavailableException("Not enough copies available for movie: " + movie.getTitle());
            }

            MovieRentalCalculationStrategy strategy = movie.getMovieType().getMoviesCalculationStrategy();
            totalCharge += strategy.calculateCharge(rentalMovie.getQuantity());
            frequentRenterPoints += strategy.calculateFrequentRenterPoints(rentalMovie.getQuantity());

            inventoryService.reduceInventory(movie.getId(), rentalMovie.getQuantity());
        }

        System.out.println("Customer: " + customer.getName());
        System.out.println("Total charge: " + totalCharge);
        System.out.println("Frequent renter points: " + frequentRenterPoints);

        rentalRepository.save(rental);
        customerRepository.updateFrequentRenterPoints(customer.getId(), customer.getFrequentRenterPoints() + frequentRenterPoints);
    }

    public void returnRental(int rentalId, List<RentalMovie> rentalMovies) {
        rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with ID: " + rentalId));
        rentalRepository.updateReturnDate(rentalId, LocalDate.now());
        for (RentalMovie rentalMovie : rentalMovies) {
            inventoryService.increaseInventory(rentalMovie.getMovieId(), rentalMovie.getQuantity());
        }
        System.out.println("Rental returned successfully!");
    }

    public List<Rental> getRentalsByCustomerId(int customerId) {
        Validator.validatePositiveNumber(customerId, "Customer ID");
        return rentalRepository.findByCustomerId(customerId);
    }
}