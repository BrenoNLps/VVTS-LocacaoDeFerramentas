package br.ifsp.demo.domain.model;

import br.ifsp.demo.annotation.Mutation;
import br.ifsp.demo.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    @UnitTest
    @Mutation
    @DisplayName("Should return id provided at construction")
    void shouldReturnIdProvidedAtConstruction() {
        Customer customer = new Customer("customer-1", "John Doe");
        assertThat(customer.getId()).isEqualTo("customer-1");
    }
}