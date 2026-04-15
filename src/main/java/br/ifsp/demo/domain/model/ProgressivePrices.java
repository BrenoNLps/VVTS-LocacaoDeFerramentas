package br.ifsp.demo.domain.model;

import lombok.Getter;

import java.math.BigDecimal;

public class ProgressivePrices {
    @Getter private final BigDecimal dailyRate;
    @Getter private final BigDecimal weeklyDailyRate;
    @Getter private final BigDecimal monthlyDailyRate;

    public ProgressivePrices(BigDecimal dailyRate, BigDecimal weeklyDailyRate, BigDecimal monthlyDailyRate) {
        this.dailyRate = dailyRate;
        this.weeklyDailyRate = weeklyDailyRate;
        this.monthlyDailyRate = monthlyDailyRate;
    }

    public BigDecimal rateFor(long days) {
        if (days < 7)  return dailyRate;
        if (days < 30)  return weeklyDailyRate;
        return monthlyDailyRate;
    }
}
