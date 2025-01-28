package org.rental.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rental {
    private int id;
    private int customerId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private List<RentalMovie> rentalMovies;

}