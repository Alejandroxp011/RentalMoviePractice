package org.rental.domain.strategies;

import org.rental.domain.strategies.interfaces.MovieRentalCalculationStrategy;
import org.rental.domain.strategies.utils.MovieRentalConstants;

public class RegularMovieCalculation implements MovieRentalCalculationStrategy {
    @Override
    public double calculateCharge(int daysRented) {
        double result = MovieRentalConstants.REGULAR_BASE_CHARGE;
        if (daysRented > MovieRentalConstants.REGULAR_BASE_DAYS) {
            result += (daysRented - MovieRentalConstants.REGULAR_BASE_DAYS) * MovieRentalConstants.REGULAR_EXTRA_CHARGE;
        }
        return result;
    }

    @Override
    public int calculateFrequentRenterPoints(int daysRented) {
        return MovieRentalConstants.BASE_RENTER_POINTS;
    }
}
