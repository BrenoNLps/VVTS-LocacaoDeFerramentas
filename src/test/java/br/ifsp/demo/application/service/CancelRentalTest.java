package br.ifsp.demo.application.service;

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
import java.util.List;

import static br.ifsp.demo.domain.model.RentalStatus.*;
import static br.ifsp.demo.domain.model.ToolStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelRentalTest {

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private CancelRental cancelRental;

    private Tool buildTool(String id, ToolStatus status) {
        return new Tool(id, "Chainsaw", status,
                new ProgressivePrices(BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6)));
    }

    @Nested
    @DisplayName("Happy path")
    class HappyPath{

        @Test
        @DisplayName("Should set tool as available and rental as cancelled when rental has one tool")
        void shouldSetToolAsAvailableAndRentalAsCancelledWhenRentalHasOneTool() {
            var tool = buildTool("tool-1", RENTED);
            Rental rental = new Rental("rental-1", List.of(tool));
            when(rentalRepository.findById("rental-1"))
                    .thenReturn(rental);

            cancelRental.execute("rental-1");

            assertThat(tool.getStatus())
                    .isEqualTo(AVAILABLE);

            assertThat(rental.getStatus())
                    .isEqualTo(CANCELLED);

            verify(rentalRepository).save(rental);
        }

        @Test
        @DisplayName("Should set all tools as available and rental as cancelled when rental has multiple tools")
        void shouldSetAllToolsAsAvailableAndRentalAsCancelledWhenRentalHasMultipleTools() {
            var tool1 = buildTool("tool-1", RENTED);
            var tool2 = buildTool("tool-2", RENTED);
            Rental rental = new Rental("rental-1", List.of(tool1, tool2));
            when(rentalRepository.findById("rental-1"))
                    .thenReturn(rental);

            cancelRental.execute("rental-1");

            assertThat(tool1.getStatus())
                    .isEqualTo(AVAILABLE);

            assertThat(tool2.getStatus())
                    .isEqualTo(AVAILABLE);

            assertThat(rental.getStatus())
                    .isEqualTo(CANCELLED);

            verify(rentalRepository)
                    .save(rental);
        }
    }
}