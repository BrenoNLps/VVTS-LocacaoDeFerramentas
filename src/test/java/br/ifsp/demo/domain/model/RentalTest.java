package br.ifsp.demo.domain.model;

import br.ifsp.demo.annotation.Mutation;
import br.ifsp.demo.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RentalTest {

    private static final LocalDate START = LocalDate.of(2026, 1, 1);
    private static final Customer CUSTOMER = new Customer("customer-1", "John Doe");
    private static final ProgressivePrices PRICES = new ProgressivePrices(
            BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6)
    );

    private Tool buildTool(String id) {
        return new Tool(id, "Hammer", ToolStatus.AVAILABLE, PRICES);
    }

    @Test
    @UnitTest
    @Mutation
    @DisplayName("Should return tools provided at creation")
    void shouldReturnToolsProvidedAtCreation() {
        Tool tool = buildTool("tool-1");
        Rental rental = Rental.create("rental-1", List.of(tool), START, CUSTOMER);
        assertThat(rental.getTools()).containsExactly(tool);
    }

    @Test
    @UnitTest
    @Mutation
    @DisplayName("Should return start date provided at creation")
    void shouldReturnStartDateProvidedAtCreation() {
        Rental rental = Rental.create("rental-1", List.of(buildTool("tool-1")), START, CUSTOMER);
        assertThat(rental.getStartDate()).isEqualTo(START);
    }

    @Test
    @UnitTest
    @Mutation
    @DisplayName("Should return customer provided at creation")
    void shouldReturnCustomerProvidedAtCreation() {
        Rental rental = Rental.create("rental-1", List.of(buildTool("tool-1")), START, CUSTOMER);
        assertThat(rental.getCustomer()).isEqualTo(CUSTOMER);
    }

    @Test
    @UnitTest
    @Mutation
    @DisplayName("Should return end date after finalization")
    void shouldReturnEndDateAfterFinalization() {
        LocalDate endDate = START.plusDays(3);
        Rental rental = Rental.create("rental-1", List.of(buildTool("tool-1")), START, CUSTOMER);
        rental.finalize(endDate);
        assertThat(rental.getEndDate()).isEqualTo(endDate);
    }
}