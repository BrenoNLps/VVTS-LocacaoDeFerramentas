package br.ifsp.demo.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class QueryRentalValue {

    public BigDecimal execute(List<String> toolIds, LocalDate startDate, LocalDate endDate){
        Objects.requireNonNull(toolIds);
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        for (String toolId : toolIds) {
            Objects.requireNonNull(toolId, "toolId");
        }
        return null;
    }
}
