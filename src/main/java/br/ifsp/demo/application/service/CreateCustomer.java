package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class CreateCustomer {

    private final CustomerRepository customerRepository;

    public CreateCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(String name) {
        Objects.requireNonNull(name, "name");
        if (name.isBlank()) throw new InvalidArgumentException("name");
        Customer customer = new Customer(UUID.randomUUID().toString(), name);
        customerRepository.save(customer);
        return customer;
    }
}