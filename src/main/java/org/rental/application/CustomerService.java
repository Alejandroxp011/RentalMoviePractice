package org.rental.application;

import org.rental.domain.entities.Customer;
import org.rental.domain.exceptions.EntityNotFoundException;
import org.rental.domain.exceptions.InvalidArgumentException;
import org.rental.infraestructure.persistence.CustomerRepository;
import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        if (customer == null || customer.getName() == null || customer.getName().isBlank()) {
            throw new InvalidArgumentException("Invalid customer data.");
        }
        customerRepository.save(customer);
    }

    public Customer findById(int id) {
        if (id <= 0) {
            throw new InvalidArgumentException("Customer ID must be greater than zero.");
        }
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));
    }

    public List<Customer> findByName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidArgumentException("Name cannot be null or blank.");
        }
        return customerRepository.findAllByName(name);
    }
}