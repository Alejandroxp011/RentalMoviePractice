CREATE TABLE customer
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE movie
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    movie_type VARCHAR(50)  NOT NULL
);

CREATE TABLE rental
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT  NOT NULL,
    rental_date DATE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE rental_movie
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    rental_id INT NOT NULL,
    movie_id  INT NOT NULL,
    quantity  INT NOT NULL,
    FOREIGN KEY (rental_id) REFERENCES rental (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id)
);

CREATE TABLE inventory
(
    movie_id         INT PRIMARY KEY,
    available_copies INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie (id)
);
