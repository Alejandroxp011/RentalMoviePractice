package movies.src.infraestructure.persistence;

import movies.src.domain.entities.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(int id);
    void save(Customer customer);
    void update(Customer customer);
    void delete(int id);
}
