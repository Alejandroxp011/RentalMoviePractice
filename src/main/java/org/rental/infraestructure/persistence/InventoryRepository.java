package org.rental.infraestructure.persistence;


import org.rental.domain.entities.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class InventoryRepository extends GenericRepository<Inventory, Integer> {

    public InventoryRepository(Connection connection) {
        super("inventory", InventoryRepository::mapResultSetToInventory);
    }

    private static Inventory mapResultSetToInventory(ResultSet rs) {
        try {
            return new Inventory(
                    rs.getInt("movie_id"),
                    rs.getInt("available_copies")
            );
        } catch (Exception e) {
            throw new RuntimeException("Error mapping ResultSet to Inventory", e);
        }
    }

    public Optional<Inventory> findByMovieId(int movieId) {
        String query = "SELECT * FROM inventory WHERE movie_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToInventory(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching inventory for movie ID: " + movieId, e);
        }
        return Optional.empty();
    }

    public void save(Inventory inventory) {
        String query = "INSERT INTO inventory (movie_id, available_copies) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE available_copies = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, inventory.getMovieId());
            stmt.setInt(2, inventory.getAvailableCopies());
            stmt.setInt(3, inventory.getAvailableCopies());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error saving inventory for movie ID: " + inventory.getMovieId(), e);
        }
    }
}