package movies.src.domain.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    private final Customer customer;
    private final Movie movie;
    private final LocalDate rentalDate;

    public Rental(Customer customer, Movie movie, LocalDate rentalDate) {
        this.customer = customer;
        this.movie = movie;
        this.rentalDate = rentalDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public long getDaysRented(LocalDate now) {
        return ChronoUnit.DAYS.between(rentalDate, now);
    }

    public double getCharge(LocalDate now) {
        return movie.calculateCharge((int) getDaysRented(now));
    }

    public int getFrequentRenterPoints(LocalDate now) {
        return movie.calculateFrequentRenterPoints((int) getDaysRented(now));
    }
}