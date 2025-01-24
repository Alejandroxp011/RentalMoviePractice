package movies.src.infraestructure.persistence;

import movies.src.domain.entities.Movie;

import java.util.Optional;

public interface MovieRepository {
    Optional<Movie> findById(int id);
    void save(Movie movie);
    void update(Movie movie);
    void delete(int id);
}
