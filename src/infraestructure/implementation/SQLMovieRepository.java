package movies.src.infraestructure.implementation;

import movies.src.domain.entities.Movie;
import movies.src.domain.enums.MovieType;
import movies.src.infraestructure.persistence.MovieRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Optional;

public class SQLMovieRepository implements MovieRepository {
    private final Connection connection;

    public SQLMovieRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Movie> findById(int id) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt("id"),
                            rs.getString("title"),
                            MovieType.valueOf(rs.getString("movie_type"))
                    ));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error finding movie by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Movie movie) {
        String sql = "INSERT INTO movies (id, title, movie_type) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movie.getId());
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getMovieType().name());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error saving movie", e);
        }
    }

    @Override
    public void update(Movie movie) {
        String sql = "UPDATE movies SET title = ?, movie_type = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getMovieType().name());
            stmt.setInt(3, movie.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating movie", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting movie", e);
        }
    }
}

