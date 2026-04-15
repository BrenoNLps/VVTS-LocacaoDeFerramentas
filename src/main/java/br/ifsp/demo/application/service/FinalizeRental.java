package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.repository.RentalRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FinalizeRental {

    private final RentalRepository rentalRepository;

    public FinalizeRental(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public BigDecimal execute(String rentalId, LocalDate endDate) {
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) throw new EntityNotFoundException("Rental", rentalId);
        BigDecimal total = rental.finalize(endDate);
        rentalRepository.save(rental);
        return total;
    }
}
