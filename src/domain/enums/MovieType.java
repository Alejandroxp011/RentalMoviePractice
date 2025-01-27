package movies.src.domain.enums;

import movies.src.domain.strategies.ChildrenMovieRentalCalculation;
import movies.src.domain.strategies.NewReleaseMovieRentalCalculation;
import movies.src.domain.strategies.interfaces.MoviesCalculationStrategy;
import movies.src.domain.strategies.RegularMovieCalculation;

public enum MovieType {

    REGULAR(new RegularMovieCalculation()),
    NEW_RELEASE(new NewReleaseMovieRentalCalculation()),
    CHILDREN(new ChildrenMovieRentalCalculation());

    private final MoviesCalculationStrategy moviesCalculationStrategy;

    MovieType(MoviesCalculationStrategy priceStrategy) {
        this.moviesCalculationStrategy = priceStrategy;
    }

    public MoviesCalculationStrategy getMoviesCalculationStrategy() {
        return moviesCalculationStrategy;
    }
}
