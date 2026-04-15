package br.ifsp.demo.exception;

import br.ifsp.demo.domain.exception.DomainException;

public class RentalAlreadyCancelledException extends DomainException {
    public RentalAlreadyCancelledException(String rentalId) {
        super("Rental is already cancelled: " + rentalId);
    }
}
