package br.ifsp.demo.domain.model;

import br.ifsp.demo.annotation.Mutation;
import br.ifsp.demo.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MaintenanceRecordTest {

    private static final LocalDate SENT = LocalDate.of(2026, 1, 1);
    private static final LocalDate RETURNED = SENT.plusDays(3);

    @Test
    @UnitTest
    @Mutation
    @DisplayName("Should return false for isOpen when record is closed")
    void shouldReturnFalseForIsOpenWhenRecordIsClosed() {
        MaintenanceRecord record = new MaintenanceRecord(SENT);
        record.close(RETURNED);
        assertThat(record.isOpen()).isFalse();
    }

    @Test
    @UnitTest
    @Mutation
    @DisplayName("Should return false for isClosed when record is open")
    void shouldReturnFalseForIsClosedWhenRecordIsOpen() {
        MaintenanceRecord record = new MaintenanceRecord(SENT);
        assertThat(record.isClosed()).isFalse();
    }
}