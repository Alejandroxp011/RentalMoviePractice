package movies.src.application;

import movies.src.domain.entities.Customer;
import movies.src.domain.entities.Rental;
import movies.src.infraestructure.persistence.CustomerRepository;
import movies.src.infraestructure.persistence.RentalRepository;

import java.time.LocalDate;
import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;

    public CustomerService(CustomerRepository customerRepository, RentalRepository rentalRepository) {
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
    }

    public String generateStatement(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        List<Rental> rentals = rentalRepository.findAllByCustomerId(customerId);
        StringBuilder statement = new StringBuilder();
        double totalCharge = 0;
        int totalFrequentRenterPoints = 0;

        statement.append("Rental Record for ").append(customer.getName()).append("\n");

        for (Rental rental : rentals) {
            double rentalCharge = rental.getCharge(LocalDate.now());
            int frequentRenterPoints = rental.getFrequentRenterPoints(LocalDate.now());
            totalCharge += rentalCharge;
            totalFrequentRenterPoints += frequentRenterPoints;

            statement.append("\t")
                    .append(rental.getMovie().getTitle())
                    .append(" (")
                    .append(rental.getDaysRented(LocalDate.now()))
                    .append(" days): $")
                    .append(String.format("%.2f", rentalCharge))
                    .append("\n");
        }

        statement.append("Total owed: $").append(String.format("%.2f", totalCharge)).append("\n");
        statement.append("Frequent renter points: ").append(totalFrequentRenterPoints);

        return statement.toString();
    }
}
