package movies.src.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String name;
    private final List<Rental> rentals;

    public Customer(String name) {
        this.name = name;
        this.rentals = new ArrayList<>();
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public int getTotalFrequentRenterPoints(LocalDate now){
        return rentals.stream().mapToInt(rental -> rental.getFrequentRenterPoint(now)).sum();
    }

    public String getName() {
        return name;
    }

    public String generateStatement(LocalDate now) {
        StringBuilder statement = new StringBuilder();
        double totalAmount = 0;
        int totalFrequentRenterPoints = getTotalFrequentRenterPoints(now);

        statement.append("Rental Record for ").append(name).append("\n");

        for (Rental rental : rentals) {
            double rentalCharge = rental.getCharge(now);
            totalAmount += rentalCharge;

            statement.append("\t")
                .append(rental.getMovie().getTitle())
                .append(" (")
                .append(rental.getDaysRented(now))
                .append(" days): $")
                .append(String.format("%.2f", rentalCharge))
                .append("\n");
        }

        statement.append("Total owed: $").append(String.format("%.2f", totalAmount)).append("\n");
        statement.append("Frequent renter points: ").append(totalFrequentRenterPoints).append("\n");

        return statement.toString();
    }
}
