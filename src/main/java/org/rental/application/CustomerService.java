package org.rental.application;

import org.rental.application.validators.Validator;
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
        Validator.validateNotNull(customer, "Customer");
        Validator.validateNotEmpty(customer.getName(), "Customer name");
        customerRepository.save(customer);
    }

    public Customer findById(int customerId) {
        Validator.validatePositiveNumber(customerId, "Customer ID");
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));
    }

    public List<Customer> findByName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidArgumentException("Name cannot be null or blank.");
        }
        return customerRepository.findAllByName(name);
    }
}