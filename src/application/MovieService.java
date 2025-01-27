package movies.src.application;

import movies.src.domain.entities.Movie;

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findMovieById(int movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found with ID: " + movieId));
    }

    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void updateMovie(Movie movie) {
        movieRepository.update(movie);
    }

    public void deleteMovie(int movieId) {
        Movie movie = findMovieById(movieId);
        if (movie.getInventory() > 0) {
            throw new IllegalStateException("Cannot delete a movie that still has inventory.");
        }
        movieRepository.delete(movieId);
    }

    public boolean isMovieAvailable(int movieId) {
        Movie movie = findMovieById(movieId);
        return movie.getInventory() > 0;
    }

    public void reduceInventory(int movieId) {
        Movie movie = findMovieById(movieId);
        if (movie.getInventory() <= 0) {
            throw new IllegalStateException("Movie not available in inventory.");
        }
        movie.setInventory(movie.getInventory() - 1);
        movieRepository.update(movie);
    }

    public void addInventory(int movieId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        Movie movie = findMovieById(movieId);
        movie.setInventory(movie.getInventory() + quantity);
        movieRepository.update(movie);
    }
}
