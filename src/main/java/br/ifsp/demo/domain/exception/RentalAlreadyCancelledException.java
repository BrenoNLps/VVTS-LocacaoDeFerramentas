package br.ifsp.demo.domain.exception;

public class RentalAlreadyCancelledException extends DomainException {
    public RentalAlreadyCancelledException(String rentalId) {
        super("Rental is already cancelled: " + rentalId);
    }
}
