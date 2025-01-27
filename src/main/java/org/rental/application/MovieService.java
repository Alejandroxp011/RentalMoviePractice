package org.rental.application;

import org.rental.application.validators.Validator;
import org.rental.domain.entities.Movie;
import org.rental.domain.exceptions.EntityNotFoundException;
import org.rental.infraestructure.persistence.MovieRepository;
import java.util.List;

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void createMovie(Movie movie) {
        Validator.validateNotNull(movie, "Movie");
        Validator.validateObjectPropertiesNotNull(movie, "Movie");

        movieRepository.save(movie);
    }

    public Movie findById(int id) {
        Validator.validatePositiveNumber(id, "Movie ID");
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with ID: " + id));
    }

    public List<Movie> findByTitle(String title) {
        Validator.validateNotNull(title, "Title");
        Validator.validateNotEmpty(title, "Title");
        return movieRepository.findAllByTitle(title);
    }

    public void updateMovie(Movie movie) {
        Validator.validateNotNull(movie, "Movie");
        Validator.validatePositiveNumber(movie.getId(), "Movie ID");
        movieRepository.update(movie);
    }

    public void deleteMovie(int id) {
        Validator.validatePositiveNumber(id, "Movie ID");
        movieRepository.delete(id);
    }
}
