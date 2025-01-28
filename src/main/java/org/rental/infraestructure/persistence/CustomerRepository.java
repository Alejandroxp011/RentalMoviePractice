package org.rental.infraestructure.persistence;

import org.rental.domain.entities.Customer;
import org.rental.domain.exceptions.SqlOperationException;
import org.rental.infraestructure.utils.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository extends GenericRepository<Customer, Integer> {

    public CustomerRepository(Connection connection) {
        super("customer", CustomerRepository::mapResultSetToCustomer);
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
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.SELECT_CUSTOMER_BY_NAME)) {
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

    public void updateFrequentRenterPoints(int customerId, int points) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.UPDATE_FREQUENT_RENTER_POINTS)) {
            stmt.setInt(1, points);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SqlOperationException("Error updating frequent renter points for customer ID: " + customerId, e);
        }
    }

    public List<Customer> findAllByName(String name) {
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.SELECT_CUSTOMERS_BY_NAME)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapResultSetToCustomer(rs));
                }
            }
        } catch (SQLException e) {
            throw new SqlOperationException("Error fetching customers by name: " + name, e);
        }
        return customers;
    }

    public void save(Customer customer) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.INSERT_CUSTOMER)) {
            stmt.setInt(1, customer.getId());
            stmt.setString(2, customer.getName());
            stmt.setInt(3, customer.getFrequentRenterPoints());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SqlOperationException("Error saving customer: " + customer.getName(), e);
        }
    }

    public void update(Customer customer) {
        try (PreparedStatement stmt = getConnection().prepareStatement(SQLQueries.UPDATE_CUSTOMER)) {
            stmt.setString(1, customer.getName());
            stmt.setInt(2, customer.getId());
            stmt.setInt(3, customer.getFrequentRenterPoints());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SqlOperationException("Error updating customer with ID: " + customer.getId(), e);
        }
    }
}