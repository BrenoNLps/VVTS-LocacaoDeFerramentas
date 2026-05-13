package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListRentals {

    private final RentalRepository rentalRepository;

    public ListRentals(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> execute() {
        return rentalRepository.findAll();
    }
}