package org.rental.infraestructure.utils;

public class SQLQueries {
    public static final String INSERT_RENTAL = "INSERT INTO rental (id, customer_id, rental_date) VALUES (?, ?, ?)";
    public static final String UPDATE_RETURN_DATE = "UPDATE rental SET return_date = ? WHERE id = ?";
    public static final String SELECT_RENTALS_BY_CUSTOMER_ID = "SELECT * FROM rental WHERE customer_id = ?";

    public static final String SELECT_CUSTOMER_BY_NAME = "SELECT * FROM customer WHERE name = ?";
    public static final String UPDATE_FREQUENT_RENTER_POINTS = "UPDATE customer SET frequent_renter_points = ? WHERE id = ?";
    public static final String SELECT_CUSTOMERS_BY_NAME = "SELECT * FROM customer WHERE name LIKE ?";
    public static final String INSERT_CUSTOMER = "INSERT INTO customer (id, name) VALUES (?, ?)";
    public static final String UPDATE_CUSTOMER = "UPDATE customer SET name = ? WHERE id = ?";

    public static final String SELECT_INVENTORY_BY_MOVIE_ID = "SELECT * FROM inventory WHERE movie_id = ?";
    public static final String INSERT_INVENTORY = "INSERT INTO inventory (movie_id, available_copies) VALUES (?, ?) ON DUPLICATE KEY UPDATE available_copies = ?";

    public static final String SELECT_RENTAL_MOVIES_BY_RENTAL_ID = "SELECT movie_id, quantity FROM rental_movie WHERE rental_id = ?";
    public static final String INSERT_RENTAL_MOVIES = "INSERT INTO rental_movie (rental_id, movie_id, quantity) VALUES (?, ?, ?)";

    public static final String SELECT_MOVIE_BY_TITLE = "SELECT * FROM movie WHERE title = ?";
    public static final String SELECT_MOVIES_BY_TITLE = "SELECT * FROM movie WHERE title LIKE ?";
    public static final String INSERT_MOVIE = "INSERT INTO movie (id, title, movie_type) VALUES (?, ?, ?)";
    public static final String UPDATE_MOVIE = "UPDATE movie SET title = ?, type = ? WHERE id = ?";
}