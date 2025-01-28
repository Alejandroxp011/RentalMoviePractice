package org.rental;

import org.rental.application.CustomerService;
import org.rental.application.InventoryService;
import org.rental.application.MovieService;
import org.rental.application.RentalService;
import org.rental.domain.entities.*;
import org.rental.domain.enums.MovieType;
import org.rental.infraestructure.DatabaseConnection;
import org.rental.infraestructure.persistence.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();

        CustomerRepository customerRepository = new CustomerRepository();
        MovieRepository movieRepository = new MovieRepository();
        RentalMovieRepository rentalMovieRepository = new RentalMovieRepository();
        RentalRepository rentalRepository = new RentalRepository(rentalMovieRepository);
        InventoryRepository inventoryRepository = new InventoryRepository();

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        MovieService movieService = new MovieService(movieRepository);
        CustomerService customerService = new CustomerService(customerRepository);
        RentalService rentalService = new RentalService(movieService, inventoryService, rentalRepository, customerRepository);

        System.out.println("\n📌 Adding test customers...");
        Customer customer = new Customer(5, "John Doe", 0);
        customerService.createCustomer(customer);

        System.out.println("\n📌 Adding test movies...");
        Movie movie1 = new Movie(1, "The Matrix", MovieType.REGULAR);
        Movie movie2 = new Movie(2, "Frozen", MovieType.CHILDREN);
        Movie movie3 = new Movie(3, "Avengers: Endgame", MovieType.NEW_RELEASE);
        movieService.createMovie(movie1);
        movieService.createMovie(movie2);
        movieService.createMovie(movie3);


        System.out.println("\n📌 Adding inventory...");
        inventoryService.addNewMovieToInventory(1, 5);
        inventoryService.addNewMovieToInventory(2, 3);
        inventoryService.addNewMovieToInventory(3, 2);


        System.out.println("\n📌 Renting movies...");
        Rental rental = new Rental(1, customer.getId(), LocalDate.now(), null, List.of(
                new RentalMovie(1, 2),
                new RentalMovie(3, 1)
        ));
        rentalService.createRental(rental);


        System.out.println("\n📌 Retrieving customer rental history...");
        List<Rental> rentals = rentalService.getRentalsByCustomerId(customer.getId());
        rentals.forEach(r -> System.out.println("Rental ID: " + r.getId() + ", Date: " + r.getRentalDate()));

        System.out.println("\n📌 Retrieving customer points...");
        Customer updatedCustomer = customerService.findById(customer.getId());
        System.out.println("Customer: " + updatedCustomer.getName());
        System.out.println("Total Frequent Renter Points: " + updatedCustomer.getFrequentRenterPoints());


        System.out.println("\n📌 Returning movies...");
        rentalService.returnRental(rental.getId(), rental.getRentalMovies());

        System.out.println("\n📌 Retrieving customer rental history...");
        rentals = rentalService.getRentalsByCustomerId(customer.getId());
        rentals.forEach(r -> System.out.println("Rental ID: " + r.getId() + " Return Date " + r.getReturnDate()));

        System.out.println("\n✅ All operations completed successfully!");
    }
}