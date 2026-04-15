package br.ifsp.demo.domain.exception;

public class RentalAlreadyFinalizedException extends DomainException {
    public RentalAlreadyFinalizedException(String rentalId) {
        super("Rental is already finalized: " + rentalId);
    }
}
