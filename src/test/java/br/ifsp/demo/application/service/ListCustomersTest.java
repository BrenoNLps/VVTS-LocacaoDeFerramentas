package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListCustomers")
class ListCustomersTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ListCustomers listCustomers;

    @Test
    @UnitTest
    @DisplayName("Should return all customers from repository")
    void shouldReturnAllCustomersFromRepository() {
        List<Customer> customers = List.of(
                new Customer("1", "John"),
                new Customer("2", "Jane")
        );
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = listCustomers.execute();

        assertThat(result).containsExactlyElementsOf(customers);
    }

    @Test
    @UnitTest
    @DisplayName("Should return empty list when no customers exist")
    void shouldReturnEmptyListWhenNoCustomersExist() {
        when(customerRepository.findAll()).thenReturn(List.of());

        List<Customer> result = listCustomers.execute();

        assertThat(result).isEmpty();
    }
}