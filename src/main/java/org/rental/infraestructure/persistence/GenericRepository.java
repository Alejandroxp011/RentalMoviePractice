package org.rental.infraestructure.persistence;

import org.rental.infraestructure.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class GenericRepository<T, ID> {
    private final Connection connection;
    private final String tableName;
    private final Function<ResultSet, T> mapper;

    protected GenericRepository(String tableName, Function<ResultSet, T> mapper) {
        this.connection = DatabaseConnection.getConnection();
        this.tableName = tableName;
        this.mapper = mapper;
    }

    protected Connection getConnection() {
        return connection;
    }

    public Optional<T> findById(ID id) {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapper.apply(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching entity by ID: " + id, e);
        }
        return Optional.empty();
    }


    public void delete(ID id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting entity with ID: " + id, e);
        }
    }
}