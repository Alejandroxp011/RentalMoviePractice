package movies.src.infraestructure.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class GenericRepository<T, ID> {
    private final Connection connection;
    private final String tableName;
    private final Function<ResultSet, T> mapper;

    protected GenericRepository(Connection connection, String tableName, Function<ResultSet, T> mapper) {
        this.connection = connection;
        this.tableName = tableName;
        this.mapper = mapper;
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
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching entity by ID: " + id, e);
        }
        return Optional.empty();
    }

    public List<T> findAll() {
        String query = "SELECT * FROM " + tableName;
        List<T> results = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                results.add(mapper.apply(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all entities from table: " + tableName, e);
        }
        return results;
    }

    public void save(T entity, String insertSQL, Object... params) {
        executeUpdate(insertSQL, params);
    }

    public void update(T entity, String updateSQL, Object... params) {
        executeUpdate(updateSQL, params);
    }

    public void delete(ID id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting entity with ID: " + id, e);
        }
    }

    private void executeUpdate(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing update: " + query, e);
        }
    }
}