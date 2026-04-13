package br.ifsp.demo.application;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.repository.ToolRepository;
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
class SendToMaintenanceServiceTest {
    @Mock private ToolRepository toolRepository;
    @InjectMocks SendToMaintenanceService sendToMaintenanceService;

    @Test
    @DisplayName("Should throw NullPointerException when tool id is null")
    void shouldThrowNullPointerExceptionWhenToolIdIsNull() {
        assertThatThrownBy(() -> sendToMaintenanceService.execute(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when tool id is empty")
    void shouldThrowIllegalArgumentExceptionWhenToolIdIsEmpty() {
        assertThatThrownBy(() -> sendToMaintenanceService.execute(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when tool is not found")
    void shouldThrowEntityNotFoundExceptionWhenToolIsNotFound() {
        String toolId = UUID.randomUUID().toString();
        when(toolRepository.findById(toolId)).thenReturn(null);
        assertThatThrownBy(() -> sendToMaintenanceService.execute(toolId)).isInstanceOf(EntityNotFoundException.class);
    }
}