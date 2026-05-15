package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.Structural;
import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.domain.exception.RentalAlreadyCancelledException;
import br.ifsp.demo.domain.exception.RentalAlreadyFinalizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static br.ifsp.demo.domain.model.RentalStatus.*;
import static br.ifsp.demo.domain.model.ToolStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelRentalTest {

    private static final LocalDate START = LocalDate.of(2026, 1, 1);
    private static final Customer CUSTOMER = new Customer("customer-1", "John Doe");

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
    class HappyPath {

        @Test
        @UnitTest
        @TDD
        @DisplayName("Should set tool as available and rental as cancelled when rental has one tool")
        void shouldSetToolAsAvailableAndRentalAsCancelledWhenRentalHasOneTool() {
            var tool = buildTool("tool-1", RENTED);
            Rental rental = Rental.create("rental-1", List.of(tool), START, CUSTOMER);
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
        @UnitTest
        @TDD
        @DisplayName("Should set all tools as available and rental as cancelled when rental has multiple tools")
        void shouldSetAllToolsAsAvailableAndRentalAsCancelledWhenRentalHasMultipleTools() {
            var tool1 = buildTool("tool-1", RENTED);
            var tool2 = buildTool("tool-2", RENTED);
            Rental rental = Rental.create("rental-1", List.of(tool1, tool2), null, null);
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

    @Nested
    @DisplayName("Business rule errors")
    class BusinessRuleErrors {

        @Test
        @UnitTest
        @TDD
        @DisplayName("Should Throw EntityNotFoundException when rental does not exist")
        void shouldThrowEntityNotFoundExceptionWhenRentalDoesNotExist() {
            when(rentalRepository.findById("rental-1"))
                    .thenReturn(null);

            assertThatThrownBy(() -> cancelRental.execute("rental-1"))
                    .isInstanceOf(EntityNotFoundException.class);
        }

        static Stream<Arguments> nonActiveRentalProvider() {
            return Stream.of(
                    Arguments.of(CANCELLED, RentalAlreadyCancelledException.class),
                    Arguments.of(FINALIZED, RentalAlreadyFinalizedException.class)
            );
        }

        @ParameterizedTest
        @MethodSource("nonActiveRentalProvider")
        @UnitTest
        @TDD
        @DisplayName("Should Throw Exception when rental is not active")
        void shouldThrowExceptionWhenRentalIsNotActive(RentalStatus status, Class<? extends Exception> expectedException) {
            Rental rental = Rental.reconstitute("rental-1", List.of(), START, status, CUSTOMER, null);
            when(rentalRepository.findById("rental-1"))
                    .thenReturn(rental);

            assertThatThrownBy(() -> cancelRental.execute("rental-1"))
                    .isInstanceOf(expectedException);
        }
    }

    @Nested
    @DisplayName("Input validation errors")
    class InputValidationErrors{
        static Stream<Arguments> nullInputsProvider(){
            return Stream.of(
                    Arguments.of((String) null)
            );
        }

        @ParameterizedTest
        @MethodSource("nullInputsProvider")
        @UnitTest
        @TDD
        @DisplayName("Should throw null pointer Exception when required field is null")
        void shouldThrowNullPointerExceptionWhenRequiredFieldIsNull(String rentalId) {
            assertThatThrownBy(() -> cancelRental.execute(rentalId))
                    .isInstanceOf(InvalidArgumentException.class);
        }

        @Test
        @UnitTest
        @Structural
        @DisplayName("Should throw InvalidArgumentException when rentalId is blank")
        void shouldThrowInvalidArgumentExceptionWhenRentalIdIsBlank() {
            assertThatThrownBy(() -> cancelRental.execute("   "))
                    .isInstanceOf(InvalidArgumentException.class);
        }
    }
}