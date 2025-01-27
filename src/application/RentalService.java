package movies.src.application;

import java.time.LocalDate;
import java.util.List;

import movies.src.domain.entities.Customer;
import movies.src.domain.entities.Movie;
import movies.src.domain.entities.Rental;
import movies.src.domain.entities.RentalMovie;
import movies.src.domain.exceptions.EntityNotFoundException;
import movies.src.domain.exceptions.InvalidArgumentException;
import movies.src.domain.strategies.interfaces.MovieRentalCalculationStrategy;
import movies.src.infraestructure.persistence.CustomerRepository;
import movies.src.infraestructure.persistence.RentalRepository;

public class RentalService {
    private final MovieService movieService;
    private final InventoryService inventoryService;
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;

    public RentalService(MovieService movieService, InventoryService inventoryService, RentalRepository rentalRepository, CustomerRepository customerRepository) {
        this.movieService = movieService;
        this.inventoryService = inventoryService;
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
    }

    public void createRental(Rental rental) {
        if (rental == null || rental.getRentalMovies().isEmpty()) {
            throw new InvalidArgumentException("Invalid rental data.");
        }

        Customer customer = customerRepository.findById(rental.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + rental.getCustomerId()));


        double totalCharge = 0;
        int frequentRenterPoints = 0;

        for (RentalMovie rentalMovie : rental.getRentalMovies()) {
            Movie movie = movieService.findById(rentalMovie.getMovieId());

            if (!inventoryService.isMovieAvailable(movie.getId(), rentalMovie.getQuantity())) {
                throw new InvalidArgumentException("Not enough copies available for movie: " + movie.getTitle());
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
        if (customerId <= 0) {
            throw new InvalidArgumentException("Customer ID must be greater than zero.");
        }
        return rentalRepository.findByCustomerId(customerId);
    }
}