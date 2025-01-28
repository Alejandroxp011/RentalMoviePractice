package org.rental.infraestructure.persistence;

import org.rental.domain.entities.Movie;
import org.rental.domain.enums.MovieType;
import org.rental.domain.exceptions.SqlOperationException;
import org.rental.infraestructure.utils.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        } catch (SQLException e) {
            throw new SqlOperationException("Error mapping ResultSet to Movie", e);
        }
    }

    public Optional<Movie> findByTitle(String title) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.SELECT_MOVIE_BY_TITLE)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToMovie(rs));
                }
            }
        } catch (SQLException e) {
            throw new SqlOperationException("Error fetching movie by title: " + title, e);
        }
        return Optional.empty();
    }

    public List<Movie> findAllByTitle(String title) {
        List<Movie> movies = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.SELECT_MOVIES_BY_TITLE)) {
            stmt.setString(1, "%" + title + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(mapResultSetToMovie(rs));
                }
            }
        } catch (SQLException e) {
            throw new SqlOperationException("Error fetching movies by title: " + title, e);
        }
        return movies;
    }

    public void save(Movie movie) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.INSERT_MOVIE)) {
            stmt.setInt(1, movie.getId());
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getMovieType().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SqlOperationException("Error saving movie: " + movie.getTitle(), e);
        }
    }

    public void update(Movie movie) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.UPDATE_MOVIE)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getMovieType().name());
            stmt.setInt(3, movie.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SqlOperationException("Error updating movie with ID: " + movie.getId(), e);
        }
    }
}