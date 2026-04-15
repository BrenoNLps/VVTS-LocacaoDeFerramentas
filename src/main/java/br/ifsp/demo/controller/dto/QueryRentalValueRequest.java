package br.ifsp.demo.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record QueryRentalValueRequest(
        List<String> toolIds,
        LocalDate startDate,
        LocalDate endDate
) {}