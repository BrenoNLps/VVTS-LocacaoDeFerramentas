package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.model.MaintenanceRecord;
import br.ifsp.demo.domain.model.ProgressivePrices;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;

import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.exception.ToolAlreadyInMaintenanceException;
import br.ifsp.demo.domain.exception.ToolInUseException;
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

@UnitTest
@TDD
@ExtendWith(MockitoExtension.class)
class SendToMaintenanceTest {
    @Mock private ToolRepository toolRepository;
    @InjectMocks
    SendToMaintenance sendToMaintenance;

    private Tool buildTool(ToolStatus status) {
        ProgressivePrices prices = new ProgressivePrices(BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6));
        return new Tool(UUID.randomUUID().toString(), "Hammer", status, prices);
    }

    @Test
    @DisplayName("Should throw NullPointerException when tool id is null")
    void shouldThrowNullPointerExceptionWhenToolIdIsNull() {
        assertThatThrownBy(() -> sendToMaintenance.execute(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when tool id is blank")
    void shouldThrowIllegalArgumentExceptionWhenToolIdIsBlank() {
        assertThatThrownBy(() -> sendToMaintenance.execute(""))
                .isInstanceOf(InvalidArgumentException.class);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when tool is not found")
    void shouldThrowEntityNotFoundExceptionWhenToolIsNotFound() {
        String toolId = UUID.randomUUID().toString();
        when(toolRepository.findById(toolId)).thenReturn(null);
        assertThatThrownBy(() -> sendToMaintenance.execute(toolId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Should generate maintenance record and change tool status to maintenance when tool is available")
    void shouldGenerateMaintenanceRecordAndChangeToolStatusWhenToolIsAvailable() {
        Tool tool = buildTool(ToolStatus.AVAILABLE);
        when(toolRepository.findById(tool.getId())).thenReturn(tool);
        MaintenanceRecord result= sendToMaintenance.execute(tool.getId());
        assertThat(result).isNotNull();
        assertThat(tool.getStatus()).isEqualTo(ToolStatus.MAINTENANCE);
        verify(toolRepository).save(tool);
    }

    @Test
    @DisplayName("Should throw ToolInUseException when tool status is rented")
    void shouldThrowToolInUseExceptionWhenToolStatusIsRented() {
        Tool tool = buildTool(ToolStatus.RENTED);
        when(toolRepository.findById(tool.getId())).thenReturn(tool);
        assertThatThrownBy(() -> sendToMaintenance.execute(tool.getId())).isInstanceOf(ToolInUseException.class);
    }

    @Test
    @DisplayName("Should throw ToolAlreadyInMaintenanceException when tool status is maintenance")
    void shouldThrowToolAlreadyInMaintenanceExceptionWhenToolStatusIsMaintenance() {
        Tool tool = buildTool(ToolStatus.MAINTENANCE);
        when(toolRepository.findById(tool.getId())).thenReturn(tool);
        assertThatThrownBy(() -> sendToMaintenance.execute(tool.getId())).isInstanceOf(ToolAlreadyInMaintenanceException.class);
    }

}