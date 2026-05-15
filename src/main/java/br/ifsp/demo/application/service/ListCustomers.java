package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCustomers {

    private final CustomerRepository customerRepository;

    public ListCustomers(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> execute() {
        return customerRepository.findAll();
    }
}