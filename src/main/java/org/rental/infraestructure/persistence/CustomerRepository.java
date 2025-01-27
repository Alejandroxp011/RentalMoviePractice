package org.rental.infraestructure.persistence;

import org.rental.domain.entities.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository extends GenericRepository<Customer, Integer> {

    public CustomerRepository(Connection connection) {
        super(connection, "customer", CustomerRepository::mapResultSetToCustomer);
    }

    private static Customer mapResultSetToCustomer(ResultSet rs) {
        try {
            return new Customer(
                    rs.getInt("id"),
                    rs.getString("name")
            );
        } catch (Exception e) {
            throw new RuntimeException("Error mapping ResultSet to Customer", e);
        }
    }

    public Optional<Customer> findByName(String name) {
        String query = "SELECT * FROM customer WHERE name = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCustomer(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching customer by name: " + name, e);
        }
        return Optional.empty();
    }

    public List<Customer> findAllByName(String name) {
        String query = "SELECT * FROM customer WHERE name LIKE ?";
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapResultSetToCustomer(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching customers by name: " + name, e);
        }
        return customers;
    }

    public void save(Customer customer) {
        String query = "INSERT INTO customer (id, name) VALUES (?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, customer.getId());
            stmt.setString(2, customer.getName());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error saving customer: " + customer.getName(), e);
        }
    }

    public void update(Customer customer) {
        String query = "UPDATE customer SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setInt(2, customer.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating customer with ID: " + customer.getId(), e);
        }
    }
}