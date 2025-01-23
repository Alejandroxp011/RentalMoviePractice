package movies.src.enums;

import movies.src.strategies.ChildrenMoviesCalculation;
import movies.src.strategies.NewReleaseMoviesCalculations;
import movies.src.strategies.interfaces.MoviesCalculationStrategy;
import movies.src.strategies.RegularMoviesCalculations;

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
