package movies.src.infraestructure.persistence;

import movies.src.domain.entities.Rental;

import java.util.List;

public interface RentalRepository {
    void save(Rental rental);
    List<Rental> findAllByCustomerId(int customerId);
}
