package movies.src.services;

import java.time.LocalDate;
import movies.src.entities.Customer;
import movies.src.entities.Movie;
import movies.src.entities.Rental;
import movies.src.entities.Store;

public class RentalManager {
    private Store store;

    public RentalManager(Store store) {
        this.store = Store.getInstance();
    }

    public Rental createRental(Customer customer, Movie movie) {
        if(!store.isMovieAvailable(movie)){
            throw new IllegalArgumentException("Movie is not available");
        }

        Rental rental = new Rental(customer, movie, LocalDate.now());
        store.reduceInventory(movie);
        customer.addRental(rental);
        return rental;
    }
}
