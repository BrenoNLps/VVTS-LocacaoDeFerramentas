package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReturnFromMaintenanceTest {
    @InjectMocks ReturnFromMaintenance returnFromMaintenance;

    @TDD @UnitTest @Test //58
    void shouldThrowNullPointerExceptionWhenToolIdIsNull() {
        assertThatThrownBy(()-> returnFromMaintenance.execute(null)).isInstanceOf(NullPointerException.class);
    }

}