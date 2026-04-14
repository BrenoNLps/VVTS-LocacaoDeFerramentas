package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.exception.InvalidPeriodException;
import br.ifsp.demo.domain.model.ProgressivePrices;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.ToolRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryRentalValueTest {
    @Mock ToolRepository toolRepository;
    @InjectMocks private QueryRentalValue queryRentalValue;

    private Tool buildTool(ToolStatus status) {
        ProgressivePrices prices = new ProgressivePrices(BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6));
        return new Tool(UUID.randomUUID().toString(), "Hammer", status, prices);
    }
    private static final ProgressivePrices PRICES = new ProgressivePrices(BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6));

    @Test @UnitTest @TDD //75
    @DisplayName("Should throw NullPointerException when tool id is null")
    void shouldThrowNullPointerExceptionWhenToolIdIsNull() {
        List<String> toolIds = new ArrayList<>();
        toolIds.add(null);
        assertThatThrownBy(() -> queryRentalValue.execute(toolIds, LocalDate.now(), LocalDate.now().plusDays(5)))
                .isInstanceOf(NullPointerException.class);
    }

    @Test @UnitTest @TDD //77
    @DisplayName("Should throw NullPointerException when start date is null")
    void shouldThrowNullPointerExceptionWhenStartDateIsNull() {
        List<String> toolIds = new ArrayList<>();
        assertThatThrownBy(() -> queryRentalValue.execute(toolIds, null, LocalDate.now().plusDays(5)))
                .isInstanceOf(NullPointerException.class);
    }

    @Test @UnitTest @TDD
    @DisplayName("Should throw NullPointerException when end date is null")
    void shouldThrowNullPointerExceptionWhenEndDateIsNull() {
        List<String> toolIds = new ArrayList<>();
        assertThatThrownBy(() -> queryRentalValue.execute(toolIds, LocalDate.now(), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test @UnitTest @TDD //75
    @DisplayName("Should throw InvalidArgumentException when tool id is blank")
    void shouldThrowInvalidArgumentExceptionWhenToolIdIsBlank(){
        List<String> toolIds = new ArrayList<>();
        toolIds.add("");
        assertThatThrownBy(() -> queryRentalValue.execute(toolIds, LocalDate.now(), LocalDate.now().plusDays(5)))
                .isInstanceOf(InvalidArgumentException.class);
    }

    @Test @UnitTest @TDD //73
    @DisplayName("Should throw InvalidPeriodException when end date is before start date")
    void shouldThrowInvalidPeriodExceptionWhenEndDateIsBeforeStartDate() {
        List<String> toolIds = List.of("tool-1");
        assertThatThrownBy(() -> queryRentalValue.execute(toolIds, LocalDate.now().plusDays(5), LocalDate.now()))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @Test @UnitTest @TDD //72
    @DisplayName("Should throw InvalidPeriodException when rental period is zero days")
    void shouldThrowInvalidPeriodExceptionWhenRentalPeriodIsZeroDays() {
        List<String> toolIds = List.of("tool-1");
        assertThatThrownBy(() -> queryRentalValue.execute(toolIds, LocalDate.now(), LocalDate.now()))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @Test @UnitTest @TDD //74
    @DisplayName("Should throw EntityNotFoundException when tool is not found")
    void shouldThrowEntityNotFoundExceptionWhenToolIsNotFound() {
        List<String> toolIds = List.of("tool-1");
        when(toolRepository.findById("tool-1")).thenReturn(null);
        assertThatThrownBy(() -> queryRentalValue.execute(toolIds, LocalDate.now(), LocalDate.now().plusDays(5)))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test @UnitTest @TDD //71
    @DisplayName("Should calculate rental value with daily rate when period is less than 7 days")
    void shouldCalculateRentalValueWithDailyRateWhenPeriodIsLessThan7Days() {
        Tool tool = new Tool("tool-1", "Hammer", ToolStatus.AVAILABLE, PRICES);
        when(toolRepository.findById("tool-1")).thenReturn(tool);
        BigDecimal result = queryRentalValue.execute(List.of("tool-1"), LocalDate.now(), LocalDate.now().plusDays(3));
        BigDecimal expected = BigDecimal.valueOf(3).multiply(BigDecimal.valueOf(10));
        assertThat(result).isEqualByComparingTo(expected);
    }

    @Test @UnitTest @TDD //31
    @DisplayName("Should calculate rental value with weekly rate when period is between 7 and 29 days")
    void shouldCalculateRentalValueWithWeeklyRateWhenPeriodIsBetween7And29Days() {
        Tool tool = new Tool("tool-1", "Hammer", ToolStatus.AVAILABLE, PRICES);
        when(toolRepository.findById("tool-1")).thenReturn(tool);
        BigDecimal result = queryRentalValue.execute(List.of("tool-1"), LocalDate.now(), LocalDate.now().plusDays(7));
        BigDecimal expected = BigDecimal.valueOf(7).multiply(BigDecimal.valueOf(8));
        assertThat(result).isEqualByComparingTo(expected);
    }

    @Test @UnitTest @TDD
    @DisplayName("Should calculate rental value with monthly rate when period is 30 days or more")
    void shouldCalculateRentalValueWithMonthlyRateWhenPeriodIs30DaysOrMore() {
        Tool tool = new Tool("tool-1", "Hammer", ToolStatus.AVAILABLE, PRICES);
        when(toolRepository.findById("tool-1")).thenReturn(tool);
        BigDecimal result = queryRentalValue.execute(List.of("tool-1"), LocalDate.now(), LocalDate.now().plusDays(30));
        BigDecimal expected = BigDecimal.valueOf(30).multiply(BigDecimal.valueOf(6));
        assertThat(result).isEqualByComparingTo(expected);
    }
}