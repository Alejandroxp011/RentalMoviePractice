package org.rental.domain.enums;

import org.rental.domain.strategies.ChildrenMovieRentalCalculation;
import org.rental.domain.strategies.NewReleaseMovieRentalCalculation;
import org.rental.domain.strategies.interfaces.MovieRentalCalculationStrategy;
import org.rental.domain.strategies.RegularMovieCalculation;

public enum MovieType {

    REGULAR(new RegularMovieCalculation()),
    NEW_RELEASE(new NewReleaseMovieRentalCalculation()),
    CHILDREN(new ChildrenMovieRentalCalculation());

    private final MovieRentalCalculationStrategy movieRentalCalculationStrategy;

    MovieType(MovieRentalCalculationStrategy priceStrategy) {
        this.movieRentalCalculationStrategy = priceStrategy;
    }

    public MovieRentalCalculationStrategy getMoviesCalculationStrategy() {
        return movieRentalCalculationStrategy;
    }
}
