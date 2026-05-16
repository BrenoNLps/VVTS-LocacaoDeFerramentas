package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.UnitTest;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListMaintenances")
class ListMaintenancesTest {

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private ListMaintenances listMaintenances;

    private Tool buildTool(String id) {
        return new Tool(id, "Drill", ToolStatus.MAINTENANCE,
                new ProgressivePrices(BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6)));
    }

    @Test
    @UnitTest
    @DisplayName("Should return all tools from repository")
    void shouldReturnAllToolsFromRepository() {
        List<Tool> tools = List.of(buildTool("tool-1"), buildTool("tool-2"));
        when(toolRepository.findAll()).thenReturn(tools);

        List<Tool> result = listMaintenances.execute();

        assertThat(result).containsExactlyElementsOf(tools);
    }

    @Test
    @UnitTest
    @DisplayName("Should return empty list when no tools exist")
    void shouldReturnEmptyListWhenNoToolsExist() {
        when(toolRepository.findAll()).thenReturn(List.of());

        List<Tool> result = listMaintenances.execute();

        assertThat(result).isEmpty();
    }
}