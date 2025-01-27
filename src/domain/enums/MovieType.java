package movies.src.domain.enums;

import movies.src.domain.strategies.ChildrenMovieRentalCalculation;
import movies.src.domain.strategies.NewReleaseMovieRentalCalculation;
import movies.src.domain.strategies.interfaces.MovieRentalCalculationStrategy;
import movies.src.domain.strategies.RegularMovieCalculation;

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
