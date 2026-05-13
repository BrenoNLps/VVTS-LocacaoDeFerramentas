package br.ifsp.demo.domain.repository;

import br.ifsp.demo.domain.model.Rental;

import java.util.List;

public interface RentalRepository {

    void save(Rental rental);

    Rental findById(String id);

    List<Rental> findAll();
}
