package movies.src.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private static Store instance;
    private List<Rental> rentals;
    private final Map<Movie, Integer> inventory;

    public Store() {
        this.inventory = new HashMap<>();
        rentals = new ArrayList<>();
    }

    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }

    public void addMovie(Movie movie, int quantity) {
        inventory.put(movie, inventory.getOrDefault(movie, 0) + quantity);
    }

    public boolean isMovieAvailable(Movie movie) {
        return inventory.getOrDefault(movie, 0) > 0;
    }

    public void reduceInventory(Movie movie) {
        if (isMovieAvailable(movie)) {
            inventory.put(movie, inventory.get(movie) - 1);
        }
    }
}
