package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.model.ProgressivePrices;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.CustomerRepository;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.domain.repository.ToolRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterRentalTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 04, 14);

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private RegisterRental registerRental;

    private Tool buildTool(String id, ToolStatus status){
        return new Tool(id, "Hammer", status,
                new ProgressivePrices(BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6)));
    }

    @Test
    @UnitTest
    @TDD
    @DisplayName("Should create rental and mark Tool as Rented when customer exists and tool is available")
    void shouldCreateRentalAndMarkToolAsRentedWhenCustomerExistsAndToolIsAvailable() {
        var customer = new Customer("customer-1", "Thom Yorke");
        var tool = buildTool("tool-1", ToolStatus.AVAILABLE);
        when(customerRepository.findById("customer-1")).thenReturn(customer);
        when(toolRepository.findById("tool-1")).thenReturn(tool);

        String rentalId = registerRental.execute(
                "customer-1", List.of("tool-1"), TODAY, "CASH_DEPOSIT", BigDecimal.TEN, null
        );

        assertThat(rentalId).isNotNull();
        assertThat(tool.getStatus()).isEqualTo(ToolStatus.RENTED);
        verify(rentalRepository).save(any());
    }
}