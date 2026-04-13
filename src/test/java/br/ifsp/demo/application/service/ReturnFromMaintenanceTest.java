package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReturnFromMaintenanceTest {
    @InjectMocks ReturnFromMaintenance returnFromMaintenance;

    @TDD @UnitTest @Test //58
    @DisplayName("Should throw NullPointerException when tool id is null")
    void shouldThrowNullPointerExceptionWhenToolIdIsNull() {
        assertThatThrownBy(()-> returnFromMaintenance.execute(null)).isInstanceOf(NullPointerException.class);
    }

    @Test @UnitTest @TDD //59
    @DisplayName("Should throw InvalidArgumentException when tool id is blank")
    void shouldThrowInvalidArgumentExceptionWhenToolIdIsBlank() {
        assertThatThrownBy(() -> returnFromMaintenance.execute("")).isInstanceOf(InvalidArgumentException.class);
    }
}