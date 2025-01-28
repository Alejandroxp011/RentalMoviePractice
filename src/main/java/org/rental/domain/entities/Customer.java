package org.rental.domain.entities;

public class Customer {
    private final int id;
    private final String name;
    private final int frequentRenterPoints;


    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.frequentRenterPoints = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFrequentRenterPoints() {
        return frequentRenterPoints;
    }
}
