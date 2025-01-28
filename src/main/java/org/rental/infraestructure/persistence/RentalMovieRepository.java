package org.rental.infraestructure.persistence;

import org.rental.domain.entities.RentalMovie;
import org.rental.domain.exceptions.SqlOperationException;
import org.rental.infraestructure.utils.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalMovieRepository extends GenericRepository<RentalMovie, Integer> {

    public RentalMovieRepository() {
        super("rental_movie", RentalMovieRepository::mapResultSetToRentalMovie);
    }

    private static RentalMovie mapResultSetToRentalMovie(ResultSet rs) {
        try {
            return new RentalMovie(
                    rs.getInt("movie_id"),
                    rs.getInt("quantity")
            );
        } catch (SQLException e) {
            throw new SqlOperationException("Error mapping ResultSet to RentalMovie", e);
        }
    }

    public List<RentalMovie> findMoviesByRentalId(int rentalId) {
        List<RentalMovie> rentalMovies = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.SELECT_RENTAL_MOVIES_BY_RENTAL_ID)) {
            stmt.setInt(1, rentalId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rentalMovies.add(mapResultSetToRentalMovie(rs));
                }
            }
        } catch (SQLException e) {
            throw new SqlOperationException("Error fetching movies for rental ID: " + rentalId, e);
        }
        return rentalMovies;
    }

    public void saveRentalMovies(int rentalId, List<RentalMovie> rentalMovies) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.INSERT_RENTAL_MOVIES)) {
            for (RentalMovie rentalMovie : rentalMovies) {
                stmt.setInt(1, rentalId);
                stmt.setInt(2, rentalMovie.getMovieId());
                stmt.setInt(3, rentalMovie.getQuantity());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new SqlOperationException("Error saving movies for rental ID: " + rentalId, e);
        }
    }
}