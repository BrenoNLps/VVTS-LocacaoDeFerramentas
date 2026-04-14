package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.exception.InvalidPeriodException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class QueryRentalValue {

    public BigDecimal execute(List<String> toolIds, LocalDate startDate, LocalDate endDate){
        Objects.requireNonNull(toolIds);
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        if (endDate.isBefore(startDate)) throw new InvalidPeriodException();
        if (startDate.isEqual(endDate)) throw new InvalidPeriodException();
        for (String toolId : toolIds) {
            Objects.requireNonNull(toolId, "toolId");
            if(toolId.isBlank()) throw new InvalidArgumentException("toolId");
        }
        return null;
    }
}
