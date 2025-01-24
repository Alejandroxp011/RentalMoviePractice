package movies.src.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final int id;
    private final String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
