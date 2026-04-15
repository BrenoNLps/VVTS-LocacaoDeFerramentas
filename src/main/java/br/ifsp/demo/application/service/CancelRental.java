package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.repository.RentalRepository;
import org.springframework.stereotype.Service;

@Service
public class CancelRental {

    private final RentalRepository rentalRepository;

    public CancelRental(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void execute(String rentalId) {
        if (rentalId == null || rentalId.isBlank()) {
            throw new InvalidArgumentException("rentalId");
        }
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) throw new EntityNotFoundException("Rental", rentalId);
        rental.cancel();
        rentalRepository.save(rental);
    }
}
