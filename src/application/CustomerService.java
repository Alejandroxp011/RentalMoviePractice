package movies.src.application;

import movies.src.domain.entities.Customer;
import movies.src.domain.exceptions.EntityNotFoundException;
import movies.src.domain.exceptions.InvalidArgumentException;
import movies.src.infraestructure.persistence.GenericRepository;

import java.util.List;

public class CustomerService {
    private final GenericRepository<Customer, Integer> customerRepository;

    public CustomerService(GenericRepository<Customer, Integer> customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        if (customer == null) {
            throw new InvalidArgumentException("Customer cannot be null.");
        }
        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new InvalidArgumentException("Customer name cannot be null or blank.");
        }
        customerRepository.save(customer);
    }

    public Customer findCustomerById(int customerId) {
        if (customerId <= 0) {
            throw new InvalidArgumentException("Customer ID must be greater than zero.");
        }
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));
    }

    public List<Customer> findCustomersByName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidArgumentException("Name cannot be null or blank.");
        }

        return customerRepository.findAllByName(name);
    }

    public void updateCustomer(Customer customer) {
        if (customer == null) {
            throw new InvalidArgumentException("Customer cannot be null.");
        }
        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new InvalidArgumentException("Customer name cannot be null or blank.");
        }
        customerRepository.update(customer);
    }

    public void deleteCustomer(int customerId) {
        if (customerId <= 0) {
            throw new InvalidArgumentException("Customer ID must be greater than zero.");
        }

        boolean hasActiveRentals = checkIfCustomerHasActiveRentals(customerId);
        if (hasActiveRentals) {
            throw new InvalidArgumentException("Cannot delete customer with active rentals.");
        }
        customerRepository.delete(customerId);
    }

    private boolean checkIfCustomerHasActiveRentals(int customerId) {
        return false;
    }
}
