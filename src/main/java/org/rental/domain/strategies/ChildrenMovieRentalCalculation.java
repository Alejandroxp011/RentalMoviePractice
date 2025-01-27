package org.rental.domain.strategies;

import org.rental.domain.strategies.interfaces.MovieRentalCalculationStrategy;
import org.rental.domain.strategies.utils.MovieRentalConstants;

public class ChildrenMovieRentalCalculation implements MovieRentalCalculationStrategy {
    @Override
    public double calculateCharge(int daysRented) {
        double result = MovieRentalConstants.CHILDREN_BASE_CHARGE;
        if (daysRented > MovieRentalConstants.CHILDREN_BASE_DAYS) {
            result += (daysRented - MovieRentalConstants.CHILDREN_BASE_DAYS) * MovieRentalConstants.CHILDREN_EXTRA_CHARGE;
        }
        return result;
    }

    @Override
    public int calculateFrequentRenterPoints(int daysRented) {
        return MovieRentalConstants.BASE_RENTER_POINTS;
    }
}
