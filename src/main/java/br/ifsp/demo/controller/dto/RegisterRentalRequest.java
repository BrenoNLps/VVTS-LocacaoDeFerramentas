package br.ifsp.demo.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record RegisterRentalRequest(
        String customerId,
        List<String> toolIds,
        LocalDate startDate,
        String guaranteeType,
        BigDecimal depositValue,
        String documentNumber
) {}
