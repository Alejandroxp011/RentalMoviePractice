package org.rental.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private int id;
    private String name;
    private int frequentRenterPoints;


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
