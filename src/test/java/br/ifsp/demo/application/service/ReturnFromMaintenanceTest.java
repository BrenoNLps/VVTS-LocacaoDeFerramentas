package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
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

@ExtendWith(MockitoExtension.class)
class ReturnFromMaintenanceTest {
    @Mock
    ToolRepository toolRepository;
    @InjectMocks ReturnFromMaintenance returnFromMaintenance;

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

}