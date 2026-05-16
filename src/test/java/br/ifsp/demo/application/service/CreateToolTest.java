package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.ToolRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateTool")
class CreateToolTest {

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private CreateTool createTool;

    @Test
    @UnitTest
    @DisplayName("Should create tool with AVAILABLE status and correct name")
    void shouldCreateToolWithAvailableStatusAndCorrectName() {
        Tool result = createTool.execute("Hammer", BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6));

        assertThat(result.getName()).isEqualTo("Hammer");
        assertThat(result.getStatus()).isEqualTo(ToolStatus.AVAILABLE);
        assertThat(result.getId()).isNotBlank();
    }

    @Test
    @UnitTest
    @DisplayName("Should persist tool to repository after creation")
    void shouldPersistToolToRepositoryAfterCreation() {
        ArgumentCaptor<Tool> captor = ArgumentCaptor.forClass(Tool.class);

        createTool.execute("Drill", BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6));

        verify(toolRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Drill");
    }
}