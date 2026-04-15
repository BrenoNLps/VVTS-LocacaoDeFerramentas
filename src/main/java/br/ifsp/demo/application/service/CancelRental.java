package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.model.RentalStatus;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.exception.RentalAlreadyCancelledException;
import br.ifsp.demo.exception.RentalAlreadyFinalizedException;

import static br.ifsp.demo.domain.model.RentalStatus.*;
import static br.ifsp.demo.domain.model.ToolStatus.*;

public class CancelRental {

    private final RentalRepository rentalRepository;

    public CancelRental(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void execute(String rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) throw new EntityNotFoundException("Rental", rentalId);
        if (rental.getStatus() == CANCELLED) throw new RentalAlreadyCancelledException(rentalId);
        if (rental.getStatus() == FINALIZED) throw new RentalAlreadyFinalizedException(rentalId);
        rental.cancel();
        rentalRepository.save(rental);
    }
}
