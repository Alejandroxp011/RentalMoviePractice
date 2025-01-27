package org.rental.application;

import org.rental.domain.entities.Movie;
import org.rental.domain.exceptions.EntityNotFoundException;
import org.rental.domain.exceptions.InvalidArgumentException;
import org.rental.infraestructure.persistence.MovieRepository;
import java.util.List;

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void createMovie(Movie movie) {
        if (movie == null) {
            throw new InvalidArgumentException("Movie cannot be null.");
        }
        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            throw new InvalidArgumentException("Movie title cannot be null or blank.");
        }
        if (movie.getMovieType() == null) {
            throw new InvalidArgumentException("Movie type cannot be null.");
        }

        if (movieRepository.findByTitle(movie.getTitle()).isPresent()) {
            throw new InvalidArgumentException("A movie with this title already exists.");
        }

        movieRepository.save(movie);
    }

    public Movie findById(int id) {
        if (id <= 0) {
            throw new InvalidArgumentException("Movie ID must be greater than zero.");
        }
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with ID: " + id));
    }

    public List<Movie> findByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidArgumentException("Movie title cannot be null or blank.");
        }
        return movieRepository.findAllByTitle(title);
    }

    public void updateMovie(Movie movie) {
        if (movie == null) {
            throw new InvalidArgumentException("Movie cannot be null.");
        }
        if (movie.getId() <= 0) {
            throw new InvalidArgumentException("Movie ID must be greater than zero.");
        }

        movieRepository.update(movie);
    }

    public void deleteMovie(int id) {
        if (id <= 0) {
            throw new InvalidArgumentException("Movie ID must be greater than zero.");
        }
        movieRepository.delete(id);
    }
}
