package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.MaintenanceRecord;
import br.ifsp.demo.domain.Tool;
import br.ifsp.demo.domain.ToolStatus;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.ToolInUseException;
import br.ifsp.demo.domain.repository.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@UnitTest
@TDD
@ExtendWith(MockitoExtension.class)
class SendToMaintenanceTest {
    private Tool tool;
    private String toolId;
    @Mock private ToolRepository toolRepository;
    @InjectMocks
    SendToMaintenance sendToMaintenance;

    @BeforeEach
    void setUp() {
        toolId= UUID.randomUUID().toString();
        tool = new Tool();
    }

    @Test
    @DisplayName("Should throw NullPointerException when tool id is null")
    void shouldThrowNullPointerExceptionWhenToolIdIsNull() {
        assertThatThrownBy(() -> sendToMaintenance.execute(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when tool id is empty")
    void shouldThrowIllegalArgumentExceptionWhenToolIdIsEmpty() {
        assertThatThrownBy(() -> sendToMaintenance.execute(""))
                .isInstanceOf(IllegalArgumentException.class);
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
        when(toolRepository.findById(toolId)).thenReturn(tool);
        MaintenanceRecord result= sendToMaintenance.execute(toolId);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should throw ToolInUseException when tool status is rented")
    void shouldThrowToolInUseExceptionWhenToolStatusIsRented() {
        tool.setStatus(ToolStatus.RENTED);
        when(toolRepository.findById(toolId)).thenReturn(tool);
        assertThatThrownBy(() -> sendToMaintenance.execute(toolId)).isInstanceOf(ToolInUseException.class);
    }

}