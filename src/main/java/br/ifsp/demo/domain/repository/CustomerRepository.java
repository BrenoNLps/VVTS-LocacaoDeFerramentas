package br.ifsp.demo.domain.repository;

import br.ifsp.demo.domain.model.Customer;

public interface CustomerRepository {
    Customer findById(String id);
}
