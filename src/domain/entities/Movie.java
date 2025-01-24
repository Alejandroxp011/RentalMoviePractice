package movies.src.domain.entities;

import movies.src.domain.enums.MovieType;

public class Movie {
    private final String title;
    private final MovieType movieType;

    public Movie(String title, MovieType movieType) {
        this.title = title;
        this.movieType = movieType;
    }

    public String getTitle() {
        return title;
    }

    public MovieType getMovieType() {
        return movieType;
    }

    public double calculateCharge(int daysRented) {
        return movieType.getMoviesCalculationStrategy().calculateCharge(daysRented);
    }

    public int calculateFrequentRenterPoints(int daysRented) {
        return movieType.getMoviesCalculationStrategy().calculateFrequentRenterPoints(daysRented);
    }
}
