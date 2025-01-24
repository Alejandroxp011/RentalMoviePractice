package movies.src.domain.enums;

import movies.src.domain.strategies.ChildrenMoviesCalculation;
import movies.src.domain.strategies.NewReleaseMoviesCalculations;
import movies.src.domain.strategies.interfaces.MoviesCalculationStrategy;
import movies.src.domain.strategies.RegularMoviesCalculations;

public enum MovieType {

    REGULAR(new RegularMoviesCalculations()),
    NEW_RELEASE(new NewReleaseMoviesCalculations()),
    CHILDREN(new ChildrenMoviesCalculation());

    private final MoviesCalculationStrategy moviesCalculationStrategy;

    MovieType(MoviesCalculationStrategy priceStrategy) {
        this.moviesCalculationStrategy = priceStrategy;
    }

    public MoviesCalculationStrategy getMoviesCalculationStrategy() {
        return moviesCalculationStrategy;
    }
}
