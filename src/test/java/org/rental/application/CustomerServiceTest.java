package org.rental.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rental.domain.entities.Customer;
import org.rental.domain.exceptions.EntityNotFoundException;
import org.rental.domain.exceptions.InvalidArgumentException;
import org.rental.infraestructure.persistence.CustomerRepository;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");

        customerService.createCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testFindById() {
        Customer customer = new Customer();
        customer.setId(1);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Customer foundCustomer = customerService.findById(1);

        assertEquals(customer, foundCustomer);
    }

    @Test
    void testFindByIdNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.findById(1));
    }

    @Test
    void testFindByName() {
        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        Customer customer2 = new Customer();
        customer2.setName("John Doe");
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAllByName("John Doe")).thenReturn(customers);

        List<Customer> foundCustomers = customerService.findByName("John Doe");

        assertEquals(2, foundCustomers.size());
        assertEquals("John Doe", foundCustomers.getFirst().getName());
    }

    @Test
    void testFindByNameInvalid() {
        assertThrows(InvalidArgumentException.class, () -> customerService.findByName(""));
    }
}