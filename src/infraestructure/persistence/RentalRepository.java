package movies.src.infraestructure.persistence;

import movies.src.domain.entities.Rental;
import movies.src.domain.entities.RentalMovie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RentalRepository extends GenericRepository<Rental, Integer> {
    private final RentalMovieRepository rentalMovieRepository;

    public RentalRepository(Connection connection, RentalMovieRepository rentalMovieRepository) {
        super(connection, "rental", rs -> mapResultSetToRental(rs, rentalMovieRepository));
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
        } catch (Exception e) {
            throw new RuntimeException("Error mapping ResultSet to Rental", e);
        }
    }

    public void save(Rental rental) {
        String query = "INSERT INTO rental (id, customer_id, rental_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, rental.getId());
            stmt.setInt(2, rental.getCustomerId());
            stmt.setDate(3, java.sql.Date.valueOf(rental.getRentalDate()));
            stmt.executeUpdate();
            rentalMovieRepository.saveRentalMovies(rental.getId(), rental.getRentalMovies());
        } catch (Exception e) {
            throw new RuntimeException("Error saving rental with ID: " + rental.getId(), e);
        }
    }

    public void updateReturnDate(int rentalId, LocalDate returnDate) {
        String query = "UPDATE rental SET return_date = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(returnDate));
            stmt.setInt(2, rentalId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating return date for rental ID: " + rentalId, e);
        }
    }

    public List<Rental> findByCustomerId(int customerId) {
        String query = "SELECT * FROM rental WHERE customer_id = ?";
        List<Rental> rentals = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rentals.add(mapResultSetToRental(rs, rentalMovieRepository));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching rentals for customer ID: " + customerId, e);
        }
        return rentals;
    }
}