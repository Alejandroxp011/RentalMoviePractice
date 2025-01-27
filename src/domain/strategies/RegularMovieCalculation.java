package movies.src.domain.strategies;

import movies.src.domain.strategies.interfaces.MoviesCalculationStrategy;

public class RegularMovieCalculation implements MoviesCalculationStrategy {
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
