package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.repository.RentalRepository;

public class CancelRental {

    private final RentalRepository rentalRepository;

    public CancelRental(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void execute(String rentalId) {

    }
}
