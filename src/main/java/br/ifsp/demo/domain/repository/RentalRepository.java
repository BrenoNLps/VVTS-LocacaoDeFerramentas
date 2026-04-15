package br.ifsp.demo.domain.repository;

import br.ifsp.demo.domain.model.Rental;

public interface RentalRepository {

    void save(Rental rental);

    Rental findById(String id);
}
