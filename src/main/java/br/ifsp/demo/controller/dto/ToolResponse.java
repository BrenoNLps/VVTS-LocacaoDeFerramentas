package br.ifsp.demo.controller.dto;

import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import java.math.BigDecimal;

public record ToolResponse(
        String id,
        String name,
        ToolStatus status,
        BigDecimal dailyRate,
        BigDecimal weeklyDailyRate,
        BigDecimal monthlyDailyRate
) {
    public static ToolResponse from(Tool tool) {
        return new ToolResponse(
                tool.getId(),
                tool.getName(),
                tool.getStatus(),
                tool.getPrices().getDailyRate(),
                tool.getPrices().getWeeklyDailyRate(),
                tool.getPrices().getMonthlyDailyRate()
        );
    }
}