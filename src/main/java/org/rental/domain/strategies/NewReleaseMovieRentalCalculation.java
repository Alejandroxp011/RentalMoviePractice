package org.rental.domain.strategies;

import org.rental.domain.strategies.interfaces.MovieRentalCalculationStrategy;
import org.rental.domain.strategies.utils.MovieRentalConstants;

public class NewReleaseMovieRentalCalculation implements MovieRentalCalculationStrategy {
    @Override
    public double calculateCharge(int daysRented) {
        return daysRented * MovieRentalConstants.NEW_RELEASE_CHARGE_PER_DAY;
    }

    @Override
    public int calculateFrequentRenterPoints(int daysRented) {
        return (daysRented > 1) ? MovieRentalConstants.BONUS_RENTER_POINTS : MovieRentalConstants.BASE_RENTER_POINTS;
    }
}
