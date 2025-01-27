package movies.src.domain.strategies.interfaces;

public interface MovieRentalCalculationStrategy {
    double calculateCharge(int daysRented);
    int calculateFrequentRenterPoints(int daysRented);
}
