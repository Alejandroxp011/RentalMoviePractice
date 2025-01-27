package org.rental.domain.entities;

public class Inventory {
    private final int movieId;
    private int availableCopies;

    public Inventory(int movieId, int availableCopies) {
        this.movieId = movieId;
        this.availableCopies = availableCopies;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
