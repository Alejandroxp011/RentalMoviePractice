package movies.src.strategies;

import movies.src.strategies.interfaces.MoviesCalculationStrategy;

public class RegularMoviesCalculations implements MoviesCalculationStrategy {
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
