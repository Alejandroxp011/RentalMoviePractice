package movies.src.domain.entities;

import movies.src.domain.enums.MovieType;

public class Movie {
    private final int id;
    private final String title;
    private final MovieType movieType;

    public Movie(int id, String title, MovieType movieType) {
        this.id = id;
        this.title = title;
        this.movieType = movieType;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MovieType getMovieType() {
        return movieType;
    }
}
