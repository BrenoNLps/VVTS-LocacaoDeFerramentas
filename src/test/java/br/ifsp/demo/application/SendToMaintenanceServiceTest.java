package br.ifsp.demo.application;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@UnitTest
@TDD
@ExtendWith(MockitoExtension.class)
class SendToMaintenanceServiceTest {
    @InjectMocks
    SendToMaintenanceService sendToMaintenanceService;

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
}