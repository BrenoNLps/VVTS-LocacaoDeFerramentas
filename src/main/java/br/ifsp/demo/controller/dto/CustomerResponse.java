package br.ifsp.demo.controller.dto;

import br.ifsp.demo.domain.model.Customer;

public record CustomerResponse(String id, String name) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName());
    }
}