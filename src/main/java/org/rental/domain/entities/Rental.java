package org.rental.domain.entities;

import java.time.LocalDate;
import java.util.List;

public class Rental {
    private final int id;
    private final int customerId;
    private final LocalDate rentalDate;
    private LocalDate returnDate;
    private final List<RentalMovie> rentalMovies;

    public Rental(int id, int customerId, LocalDate rentalDate, List<RentalMovie> rentalMovies) {
        this.id = id;
        this.customerId = customerId;
        this.rentalDate = rentalDate;
        this.rentalMovies = rentalMovies;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public List<RentalMovie> getRentalMovies() {
        return rentalMovies;
    }
}