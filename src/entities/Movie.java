package movies.src.entities;

import movies.src.enums.MovieType;

public class Movie {
    private MovieType movieType;
    private final String title;

    public Movie(String title, MovieType movieType) {
        this.title = title;
        this.movieType = movieType;
    }

    public String getTitle() {
        return title;
    }

    public MovieType getMovieType(){
        return movieType;
    }

    public double calculateCharge(int daysRented){
        return movieType.getMoviesCalculationStrategy().calculateCharge(daysRented);
    }
}
