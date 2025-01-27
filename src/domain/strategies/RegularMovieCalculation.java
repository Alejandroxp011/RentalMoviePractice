package movies.src.domain.strategies;

import movies.src.domain.strategies.interfaces.MovieRentalCalculationStrategy;

public class RegularMovieCalculation implements MovieRentalCalculationStrategy {
    @Override
    public double calculateCharge(int daysRented) {
        double result = 2;
        if (daysRented > 2) {
            result += (daysRented - 2) * 1.5;
        }
        return result;
    }

    @Override
    public int calculateFrequentRenterPoints(int daysRented) {
        return 1;
    }
}
