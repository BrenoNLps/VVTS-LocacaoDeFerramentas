package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.exception.InvalidPeriodException;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.repository.ToolRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class QueryRentalValue {
    private final ToolRepository toolRepository;

    public QueryRentalValue(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public BigDecimal execute(List<String> toolIds, LocalDate startDate, LocalDate endDate){
        Objects.requireNonNull(toolIds);
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days <= 0) throw new InvalidPeriodException();
        for (String toolId : toolIds) {
            Objects.requireNonNull(toolId, "toolId");
            if(toolId.isBlank()) throw new InvalidArgumentException("toolId");
            Tool tool = toolRepository.findById(toolId);
            if (tool == null) throw new EntityNotFoundException("Tool", toolId);
        }
        return null;
    }
}
