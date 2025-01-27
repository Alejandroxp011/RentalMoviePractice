package movies.src.domain.strategies;

import movies.src.domain.strategies.interfaces.MovieRentalCalculationStrategy;

public class NewReleaseMovieRentalCalculation implements MovieRentalCalculationStrategy {
    @Override
    public double calculateCharge(int daysRented) {
        return daysRented * 3;
    }

    @Override
    public int calculateFrequentRenterPoints(int daysRented) {
        return (daysRented > 1) ? 2 : 1;
    }
}
