package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.repository.RentalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListRentals")
class ListRentalsTest {

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private ListRentals listRentals;

    @Test
    @UnitTest
    @DisplayName("Should return all rentals from repository")
    void shouldReturnAllRentalsFromRepository() {
        List<Rental> rentals = List.of(
                Rental.create("rental-1", List.of(), LocalDate.now(), new Customer("c1", "John")),
                Rental.create("rental-2", List.of(), LocalDate.now(), new Customer("c2", "Jane"))
        );
        when(rentalRepository.findAll()).thenReturn(rentals);

        List<Rental> result = listRentals.execute();

        assertThat(result).containsExactlyElementsOf(rentals);
    }

    @Test
    @UnitTest
    @DisplayName("Should return empty list when no rentals exist")
    void shouldReturnEmptyListWhenNoRentalsExist() {
        when(rentalRepository.findAll()).thenReturn(List.of());

        List<Rental> result = listRentals.execute();

        assertThat(result).isEmpty();
    }
}