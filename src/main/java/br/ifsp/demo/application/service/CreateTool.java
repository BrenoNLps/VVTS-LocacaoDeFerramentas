package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.ProgressivePrices;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CreateTool {

    private final ToolRepository toolRepository;

    public CreateTool(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public Tool execute(String name, BigDecimal dailyRate, BigDecimal weeklyDailyRate, BigDecimal monthlyDailyRate) {
        Tool tool = new Tool(
                UUID.randomUUID().toString(),
                name,
                ToolStatus.AVAILABLE,
                new ProgressivePrices(dailyRate, weeklyDailyRate, monthlyDailyRate)
        );
        toolRepository.save(tool);
        return tool;
    }
}
