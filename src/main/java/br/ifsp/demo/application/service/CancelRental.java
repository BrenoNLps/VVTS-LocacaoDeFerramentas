package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.model.RentalStatus;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.RentalRepository;

import static br.ifsp.demo.domain.model.RentalStatus.*;
import static br.ifsp.demo.domain.model.ToolStatus.*;

public class CancelRental {

    private final RentalRepository rentalRepository;

    public CancelRental(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void execute(String rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        rental.cancel();
        rentalRepository.save(rental);
    }
}
