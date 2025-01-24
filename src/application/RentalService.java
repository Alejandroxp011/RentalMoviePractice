package movies.src.application;

import java.time.LocalDate;
import movies.src.domain.entities.Customer;
import movies.src.domain.entities.Movie;
import movies.src.domain.entities.Rental;
import movies.src.infraestructure.persistence.CustomerRepository;
import movies.src.infraestructure.persistence.MovieRepository;
import movies.src.infraestructure.persistence.RentalRepository;

public class RentalService {
    private final StoreService storeService;
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final MovieRepository movieRepository;

    public RentalService(StoreService storeService,
                         RentalRepository rentalRepository,
                         CustomerRepository customerRepository,
                         MovieRepository movieRepository) {
        this.storeService = storeService;
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.movieRepository = movieRepository;
    }

    public void rentMovie(int customerId, int movieId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        if (!storeService.isMovieAvailable(movie)) {
            throw new IllegalStateException("Movie not available.");
        }

        storeService.reduceInventory(movie);

        Rental rental = new Rental(customer, movie, LocalDate.now());
        rentalRepository.save(rental);
    }
}
