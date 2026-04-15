package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.Functional;
import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.model.*;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.domain.exception.InvalidDateException;
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

        @Test
        @UnitTest
        @TDD
        @DisplayName("Should finalize rental and return consolidated total value for multiple tools")
        void shouldFinalizeRentalAndReturnConsolidatedTotalValueForMultipleTools() {
            Tool tool1 = buildTool("tool-1");
            Tool tool2 = new Tool(
                    "tool-2", "concrete mixer", RENTED,
                    new ProgressivePrices(
                            BigDecimal.valueOf(20),
                            BigDecimal.valueOf(16),
                            BigDecimal.valueOf(12))
            );

            Rental rental = new Rental("rental-1", List.of(tool1, tool2), START);
            when(rentalRepository.findById("rental-1"))
                    .thenReturn(rental);

            BigDecimal result = finalizeRental.execute("rental-1", END);

            assertThat(result).isEqualByComparingTo(new BigDecimal("90"));
            assertThat(tool1.isAvailable()).isTrue();
            assertThat(tool2.isAvailable()).isTrue();
            assertThat(rental.getStatus()).isEqualTo(FINALIZED);
        }

        @Nested
        @DisplayName("Input validation errors")
        class InputValidationErrors {
            @Test
            @UnitTest
            @TDD
            @DisplayName("Should throw NullPointerException when rentalId is null")
            void shouldThrowNullPointerExceptionWhenRentalIdIsNull() {
                assertThatThrownBy(() -> finalizeRental.execute(null, END))
                        .isInstanceOf(NullPointerException.class);
            }

            static Stream<String> blankRentalIdProvider() {
                return Stream.of("", "  ");
            }

            @Test
            @UnitTest
            @Functional
            @DisplayName("Should throw NullPointerException when endDate is null")
            void shouldThrowNullPointerExceptionWhenEndDateIsNull() {
                Tool tool = buildTool("tool-1");
                Rental rental = new Rental("rental-1", List.of(tool), START);
                when(rentalRepository.findById("rental-1")).thenReturn(rental);
                assertThatThrownBy(() -> finalizeRental.execute("rental-1", null))
                        .isInstanceOf(NullPointerException.class);
            }

            @ParameterizedTest
            @UnitTest
            @TDD
            @MethodSource("blankRentalIdProvider")
            @DisplayName("Should throw InvalidArgumentException when rentalId is blank")
            void shouldThrowInvalidArgumentExceptionWhenRentalIdIsBlank(String rentalId) {
                assertThatThrownBy(() -> finalizeRental.execute(rentalId, END))
                        .isInstanceOf(InvalidArgumentException.class);
            }

        }
    }

    @Nested
    @DisplayName("Business rule errors")
    class BusinessRuleErrors {

        static Stream<Arguments> nonActiveRentalProvider() {
            return Stream.of(
                    Arguments.of(FINALIZED, RentalAlreadyFinalizedException.class),
                    Arguments.of(CANCELLED, RentalAlreadyCancelledException.class)
            );
        }

        @ParameterizedTest
        @MethodSource("nonActiveRentalProvider")
        @UnitTest
        @TDD
        @DisplayName("Should throw exception when rental is not active")
        void shouldThrowExceptionWhenRentalIsNotActive(
                RentalStatus status, Class<? extends Exception> expectedException) {
            Tool tool = buildTool("tool-1");
            Rental rental = new Rental("rental-1", List.of(tool), status);
            when(rentalRepository.findById("rental-1"))
                    .thenReturn(rental);

            assertThatThrownBy(() -> finalizeRental.execute("rental-1", END))
                    .isInstanceOf(expectedException);
        }

        @Test
        @UnitTest
        @TDD
        @DisplayName("Should throw EntityNotFoundException when rental does not exist")
        void shouldThrowEntityNotFoundExceptionWhenRentalDoesNotExist() {
            when(rentalRepository.findById("rental-1")).thenReturn(null);

            assertThatThrownBy(() -> finalizeRental.execute("rental-1", END))
                    .isInstanceOf(EntityNotFoundException.class);
        }

        @Test
        @UnitTest
        @TDD
        @DisplayName("Should Throw InvalidDateException when end date is before start date")
        void shouldThrowInvalidDateExceptionWhenEndDateIsBeforeStartDate() {
            Tool tool = buildTool("tool-1");
            Rental rental = new Rental("rental-1", List.of(tool), START);
            when(rentalRepository.findById("rental-1"))
                    .thenReturn(rental);

            assertThatThrownBy(() -> finalizeRental.execute("rental-1", START.minusDays(1)))
                    .isInstanceOf(InvalidDateException.class);
        }

    }

    @Nested
    @DisplayName("Functional - boundary values")
    class BoundaryValues{

        static Stream<Arguments> periodTierBoundaryProvider(){
            return Stream.of(
                    Arguments.of(1, new BigDecimal("10")),
                    Arguments.of(6, new BigDecimal("60")),

                    Arguments.of(7, new BigDecimal("56")),
                    Arguments.of(29, new BigDecimal("232")),

                    Arguments.of(30, new BigDecimal("180")),
                    Arguments.of(31, new BigDecimal("186"))
            );
        }

        @ParameterizedTest
        @MethodSource("periodTierBoundaryProvider")
        @UnitTest
        @Functional
        @DisplayName("Should apply correct rate at period tier boundaries")
        void shouldApplyCorrectRateAtPeriodTierBoundaries(int days, BigDecimal expectedValue) {
            Tool tool = buildTool("tool-1");
            Rental rental = new Rental("rental-1", List.of(tool), START);
            when(rentalRepository.findById("rental-1"))
                    .thenReturn(rental);

            BigDecimal result = finalizeRental.execute("rental-1", START.plusDays(days));

            assertThat(result)
                    .isEqualByComparingTo(expectedValue);
        }

        @Test
        @UnitTest
        @Functional
        @DisplayName("Should charge minimum 1 daily rate when endDate equals startDate")
        void shouldChargeMinimum1DailyRateWhenEndDateEqualsStartDate() {
            Tool tool = buildTool("tool-1");
            Rental rental = new Rental("rental-1", List.of(tool), START);
            when(rentalRepository.findById("rental-1")).thenReturn(rental);

            BigDecimal result = finalizeRental.execute("rental-1", START);

            assertThat(result).isEqualByComparingTo(new BigDecimal(10));
        }


    }
}
