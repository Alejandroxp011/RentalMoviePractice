package movies.src.infraestructure.implementation;

import movies.src.domain.entities.Customer;
import movies.src.infraestructure.persistence.CustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class SQLCustomerRepository implements CustomerRepository {
    private final Connection connection;

    public SQLCustomerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Customer> findById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Customer(
                            rs.getInt("id"),
                            rs.getString("name")
                    ));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error finding customer by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customers (id, name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer.getId());
            stmt.setString(2, customer.getName());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error saving customer", e);
        }
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customers SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setInt(2, customer.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating customer", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting customer", e);
        }
    }
}
