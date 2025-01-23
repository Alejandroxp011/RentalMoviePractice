package movies.src;

import java.time.LocalDate;
import movies.src.entities.Customer;
import movies.src.entities.Movie;
import movies.src.entities.Rental;
import movies.src.enums.MovieType;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("John Doe");

        Movie movie1 = new Movie("Inception", MovieType.NEW_RELEASE);
        Movie movie2 = new Movie("Toy Story", MovieType.CHILDREN);

        Rental rental1 = new Rental(customer, movie1, LocalDate.now().minusDays(3));
        Rental rental2 = new Rental(customer, movie2, LocalDate.now().minusDays(5));

        customer.addRental(rental1);
        customer.addRental(rental2);

        String statement = customer.generateStatement(LocalDate.now());
        System.out.println(statement);
    }
}
