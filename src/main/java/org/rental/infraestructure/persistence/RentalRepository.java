package org.rental.infraestructure.persistence;

import org.rental.domain.entities.Rental;
import org.rental.domain.entities.RentalMovie;
import org.rental.domain.exceptions.SqlOperationException;
import org.rental.infraestructure.utils.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RentalRepository extends GenericRepository<Rental, Integer> {
    private final RentalMovieRepository rentalMovieRepository;

    public RentalRepository(Connection connection, RentalMovieRepository rentalMovieRepository) {
        super( "rental", rs -> mapResultSetToRental(rs, rentalMovieRepository));
        this.rentalMovieRepository = rentalMovieRepository;
    }

    private static Rental mapResultSetToRental(ResultSet rs, RentalMovieRepository rentalMovieRepository) {
        try {
            List<RentalMovie> rentalMovies = rentalMovieRepository.findMoviesByRentalId(rs.getInt("id"));
            return new Rental(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getDate("rental_date").toLocalDate(),
                    rentalMovies
            );
        } catch (SQLException e) {
            throw new SqlOperationException("Error mapping ResultSet to Rental", e);
        }
    }

    public void save(Rental rental) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.INSERT_RENTAL)) {
            stmt.setInt(1, rental.getId());
            stmt.setInt(2, rental.getCustomerId());
            stmt.setDate(3, java.sql.Date.valueOf(rental.getRentalDate()));
            stmt.executeUpdate();
            rentalMovieRepository.saveRentalMovies(rental.getId(), rental.getRentalMovies());
        } catch (SQLException e) {
            throw new SqlOperationException("Error saving rental with ID: " + rental.getId(), e);
        }
    }

    public void updateReturnDate(int rentalId, LocalDate returnDate) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.UPDATE_RETURN_DATE)) {
            stmt.setDate(1, java.sql.Date.valueOf(returnDate));
            stmt.setInt(2, rentalId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SqlOperationException("Error updating return date for rental ID: " + rentalId, e);
        }
    }

    public List<Rental> findByCustomerId(int customerId) {
        List<Rental> rentals = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.SELECT_RENTALS_BY_CUSTOMER_ID)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rentals.add(mapResultSetToRental(rs, rentalMovieRepository));
                }
            }
        } catch (SQLException e) {
            throw new SqlOperationException("Error fetching rentals for customer ID: " + customerId, e);
        }
        return rentals;
    }
}