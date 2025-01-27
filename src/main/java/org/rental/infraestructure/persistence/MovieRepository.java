package org.rental.infraestructure.persistence;

import org.rental.domain.entities.Movie;
import org.rental.domain.enums.MovieType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepository extends GenericRepository<Movie, Integer> {

    public MovieRepository(Connection connection) {
        super( "movie", MovieRepository::mapResultSetToMovie);
    }

    private static Movie mapResultSetToMovie(ResultSet rs) {
        try {
            return new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    MovieType.valueOf(rs.getString("type"))
            );
        } catch (Exception e) {
            throw new RuntimeException("Error mapping ResultSet to Movie", e);
        }
    }

    public Optional<Movie> findByTitle(String title) {
        String query = "SELECT * FROM movie WHERE title = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToMovie(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching movie by title: " + title, e);
        }
        return Optional.empty();
    }

    public List<Movie> findAllByTitle(String title) {
        String query = "SELECT * FROM movie WHERE title LIKE ?";
        List<Movie> movies = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, "%" + title + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(mapResultSetToMovie(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching movies by title: " + title, e);
        }
        return movies;
    }

    public void save(Movie movie) {
        String query = "INSERT INTO movie (id, title, type) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, movie.getId());
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getMovieType().name());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error saving movie: " + movie.getTitle(), e);
        }
    }

    public void update(Movie movie) {
        String query = "UPDATE movie SET title = ?, type = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getMovieType().name());
            stmt.setInt(3, movie.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating movie with ID: " + movie.getId(), e);
        }
    }
}