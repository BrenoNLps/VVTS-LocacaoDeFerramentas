package br.ifsp.demo.domain.model;

import java.math.BigDecimal;

public class ProgressivePrices {
    private final BigDecimal dailyRate;
    private final BigDecimal weeklyDailyRate;
    private final BigDecimal monthlyDailyRate;

    public ProgressivePrices(BigDecimal dailyRate, BigDecimal weeklyDailyRate, BigDecimal monthlyDailyRate) {
        this.dailyRate = dailyRate;
        this.weeklyDailyRate = weeklyDailyRate;
        this.monthlyDailyRate = monthlyDailyRate;
    }
}
