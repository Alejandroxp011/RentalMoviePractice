package org.rental.infraestructure.persistence;


import org.rental.domain.entities.Inventory;
import org.rental.domain.exceptions.SqlOperationException;
import org.rental.infraestructure.utils.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        } catch (SQLException e) {
            throw new SqlOperationException("Error mapping ResultSet to Inventory", e);
        }
    }

    public Optional<Inventory> findByMovieId(int movieId) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.SELECT_INVENTORY_BY_MOVIE_ID)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToInventory(rs));
                }
            }
        } catch (SQLException e) {
            throw new SqlOperationException("Error fetching inventory for movie ID: " + movieId, e);
        }
        return Optional.empty();
    }

    public void save(Inventory inventory) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.INSERT_INVENTORY)) {
            stmt.setInt(1, inventory.getMovieId());
            stmt.setInt(2, inventory.getAvailableCopies());
            stmt.setInt(3, inventory.getAvailableCopies());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SqlOperationException("Error saving inventory for movie ID: " + inventory.getMovieId(), e);
        }
    }
}