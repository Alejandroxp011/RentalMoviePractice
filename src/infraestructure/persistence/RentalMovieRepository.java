package movies.src.infraestructure.persistence;

import movies.src.domain.entities.RentalMovie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RentalMovieRepository extends GenericRepository<RentalMovie, Integer> {

    public RentalMovieRepository(Connection connection) {
        super(connection, "rental_movie", RentalMovieRepository::mapResultSetToRentalMovie);
    }

    private static RentalMovie mapResultSetToRentalMovie(ResultSet rs) {
        try {
            return new RentalMovie(
                    rs.getInt("movie_id"),
                    rs.getInt("quantity")
            );
        } catch (Exception e) {
            throw new RuntimeException("Error mapping ResultSet to RentalMovie", e);
        }
    }

    public List<RentalMovie> findMoviesByRentalId(int rentalId) {
        String query = "SELECT movie_id, quantity FROM rental_movie WHERE rental_id = ?";
        List<RentalMovie> rentalMovies = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rentalMovies.add(mapResultSetToRentalMovie(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching movies for rental ID: " + rentalId, e);
        }
        return rentalMovies;
    }

    public void saveRentalMovies(int rentalId, List<RentalMovie> rentalMovies) {
        String query = "INSERT INTO rental_movie (rental_id, movie_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            for (RentalMovie rentalMovie : rentalMovies) {
                stmt.setInt(1, rentalId);
                stmt.setInt(2, rentalMovie.getMovieId());
                stmt.setInt(3, rentalMovie.getQuantity());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException("Error saving movies for rental ID: " + rentalId, e);
        }
    }
}