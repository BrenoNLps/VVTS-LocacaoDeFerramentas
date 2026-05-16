package br.ifsp.demo.domain.model;

import br.ifsp.demo.annotation.Mutation;
import br.ifsp.demo.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ToolTest {

    private static final ProgressivePrices PRICES = new ProgressivePrices(
            BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6)
    );

    @Test
    @UnitTest
    @Mutation
    @DisplayName("Should return maintenance history containing added record")
    void shouldReturnMaintenanceHistoryContainingAddedRecord() {
        Tool tool = new Tool("tool-1", "Hammer", ToolStatus.AVAILABLE, PRICES);
        MaintenanceRecord record = new MaintenanceRecord(LocalDate.of(2026, 1, 1));
        tool.addMaintenanceRecord(record);
        assertThat(tool.getMaintenanceHistory()).containsExactly(record);
    }
}