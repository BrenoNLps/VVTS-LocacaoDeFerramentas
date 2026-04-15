package br.ifsp.demo.exception;

import br.ifsp.demo.domain.exception.DomainException;

public class RentalAlreadyFinalizedException extends DomainException {
    public RentalAlreadyFinalizedException(String rentalId) {
        super("Rental is already finalized: " + rentalId);
    }
}
