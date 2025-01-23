package movies.src.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    private Customer customer;
    private Movie movie;
    private LocalDate dateRented;

    public Rental(Customer customer, Movie movie, LocalDate dateRented) {
        this.customer = customer;
        this.movie = movie;
        this.dateRented = dateRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDate getDateRented() {
        return dateRented;
    }

    public int getDaysRented(LocalDate now) {
        return (int) ChronoUnit.DAYS.between(dateRented, now);
    }

    public double getCharge(LocalDate now) {
        return movie.calculateCharge(getDaysRented(now));
    }

    public int getFrequentRenterPoint(LocalDate now){
        return movie.getMovieType().getMoviesCalculationStrategy()
            .calculateFrequentRenterPoints(getDaysRented(now));
    }

    @Override
    public String toString() {
        return movie.getTitle() + " (" + dateRented + ")";
    }
}