package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.domain.repository.RentalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static br.ifsp.demo.domain.model.RentalStatus.*;
import static br.ifsp.demo.domain.model.ToolStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Finalize Rental")
class FinalizeRentalTest {

    private static final LocalDate START = LocalDate.of(2026, 1, 1);
    private static final LocalDate END = START.plusDays(3);
    private static final ProgressivePrices PRICES = new ProgressivePrices(
            BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6)
    );

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    FinalizeRental finalizeRental;

    private Tool buildTool(String id) {
        return new Tool(id, "Screwdriver", RENTED, PRICES);
    }

    @Nested
    @DisplayName("Happy path")
    class HappyPath {

        @Test
        @UnitTest
        @TDD
        @DisplayName("Should finalize rental and return total value for single tool")
        void shouldFinalizeRentalAndReturnTotalValueForSingleTool() {
            Tool tool = buildTool("tool-1");
            Rental rental = new Rental("rental-1", List.of(tool), START);
            when(rentalRepository.findById("rental-1"))
                    .thenReturn(rental);

            BigDecimal result = finalizeRental.execute("rental-1", END);

            assertThat(result).isEqualByComparingTo(new BigDecimal("30"));
            assertThat(tool.isAvailable()).isTrue();
            assertThat(rental.getStatus()).isEqualTo(FINALIZED);
        }
    }
}