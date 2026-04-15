package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.model.RentalStatus;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.exception.RentalAlreadyCancelledException;
import br.ifsp.demo.exception.RentalAlreadyFinalizedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static br.ifsp.demo.domain.model.RentalStatus.*;
import static br.ifsp.demo.domain.model.ToolStatus.*;

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
