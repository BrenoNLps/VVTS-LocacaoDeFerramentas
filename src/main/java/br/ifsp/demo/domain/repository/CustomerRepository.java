package br.ifsp.demo.domain.repository;

import br.ifsp.demo.domain.model.Customer;

import java.util.List;

public interface CustomerRepository {
    void save(Customer customer);
    Customer findById(String id);
    List<Customer> findAll();
}
