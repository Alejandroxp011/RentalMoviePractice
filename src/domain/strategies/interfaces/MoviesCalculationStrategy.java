package movies.src.domain.strategies.interfaces;

public interface MoviesCalculationStrategy {
    double calculateCharge(int daysRented);
    int calculateFrequentRenterPoints(int daysRented);
}
