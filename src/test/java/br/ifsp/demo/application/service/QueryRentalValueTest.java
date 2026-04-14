package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QueryRentalValueTest {
    @Mock ToolRepository toolRepository;
    @InjectMocks private QueryRentalValue queryRentalValue;

    private Tool buildTool(ToolStatus status) {
        ProgressivePrices prices = new ProgressivePrices(BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6));
        return new Tool(UUID.randomUUID().toString(), "Hammer", status, prices);
    }

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
}