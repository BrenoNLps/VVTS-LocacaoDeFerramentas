package br.ifsp.demo.controller.dto;

import br.ifsp.demo.domain.model.GuaranteeType;
import java.time.LocalDate;
import java.util.List;

public record RegisterRentalRequest(
        String customerId,
        List<String> toolIds,
        LocalDate startDate,
        GuaranteeType guaranteeType
) {}
