package org.rental.domain.entities;

public class RentalMovie {
    private final int movieId;
    private final int quantity;

    public RentalMovie(int movieId, int quantity) {
        this.movieId = movieId;
        this.quantity = quantity;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getQuantity() {
        return quantity;
    }
}
