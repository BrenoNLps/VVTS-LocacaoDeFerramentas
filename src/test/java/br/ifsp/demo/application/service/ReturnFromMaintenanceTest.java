package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.exception.InvalidToolStateException;
import br.ifsp.demo.domain.model.MaintenanceRecord;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReturnFromMaintenanceTest {
    @Mock
    ToolRepository toolRepository;
    @InjectMocks ReturnFromMaintenance returnFromMaintenance;

    private Tool buildTool(ToolStatus status) {
        ProgressivePrices prices = new ProgressivePrices(BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6));
        return new Tool(UUID.randomUUID().toString(), "Hammer", status, prices);
    }

    @TDD @UnitTest @Test //58
    @DisplayName("Should throw NullPointerException when tool id is null")
    void shouldThrowNullPointerExceptionWhenToolIdIsNull() {
        assertThatThrownBy(()-> returnFromMaintenance.execute(null)).isInstanceOf(NullPointerException.class);
    }

    @UnitTest @TDD @Test //59
    @DisplayName("Should throw InvalidArgumentException when tool id is blank")
    void shouldThrowInvalidArgumentExceptionWhenToolIdIsBlank() {
        assertThatThrownBy(() -> returnFromMaintenance.execute("")).isInstanceOf(InvalidArgumentException.class);
    }

    @Test @UnitTest @TDD //57
    @DisplayName("Should throw EntityNotFoundException when tool is not found")
    void shouldThrowEntityNotFoundExceptionWhenToolIsNotFound() {
        String toolId = UUID.randomUUID().toString();
        when(toolRepository.findById(toolId)).thenReturn(null);
        assertThatThrownBy(() -> returnFromMaintenance.execute(toolId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test @UnitTest @TDD //54
    @DisplayName("Should close maintenance record and change status to available when tool is in maintenance")
    void shouldCloseMaintenanceRecordAndChangeStatusToAvailableWhenToolIsInMaintenance() {
        Tool tool = buildTool(ToolStatus.MAINTENANCE);
        when(toolRepository.findById(tool.getId())).thenReturn(tool);
        MaintenanceRecord record = returnFromMaintenance.execute(tool.getId());

        assertThat(tool.getStatus()).isEqualTo(ToolStatus.AVAILABLE);
        assertThat(record).isNotNull();
        verify(toolRepository).save(tool);
    }


    @Test @UnitTest @TDD //55
    @DisplayName("Should throw InvalidToolStateException when tool status is available")
    void shouldThrowInvalidToolStateExceptionWhenToolIsAvailable() {
        Tool tool = buildTool(ToolStatus.AVAILABLE);
        when(toolRepository.findById(tool.getId())).thenReturn(tool);
        assertThatThrownBy(() -> returnFromMaintenance.execute(tool.getId())).isInstanceOf(InvalidToolStateException.class);
    }

    @Test @UnitTest @TDD //56
    @DisplayName("Should throw InvalidToolStateException when tool status is rented")
    void shouldThrowInvalidToolStateExceptionWhenToolIsRented() {
        Tool tool = buildTool(ToolStatus.RENTED);
        when(toolRepository.findById(tool.getId())).thenReturn(tool);
        assertThatThrownBy(() -> returnFromMaintenance.execute(tool.getId())).isInstanceOf(InvalidToolStateException.class);
    }
}