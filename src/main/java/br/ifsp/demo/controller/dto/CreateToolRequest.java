package br.ifsp.demo.controller.dto;

import java.math.BigDecimal;

public record CreateToolRequest(
        String name,
        BigDecimal dailyRate,
        BigDecimal weeklyDailyRate,
        BigDecimal monthlyDailyRate
) {}
