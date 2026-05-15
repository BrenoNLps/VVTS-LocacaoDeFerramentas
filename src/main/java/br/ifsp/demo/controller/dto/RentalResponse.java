package br.ifsp.demo.controller.dto;

import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.model.RentalStatus;

import java.time.LocalDate;
import java.util.List;

public record RentalResponse(
        String id,
        RentalStatus status,
        LocalDate startDate,
        LocalDate endDate,
        String customerName,
        List<ToolResponse> tools
) {
    public static RentalResponse from(Rental rental) {
        return new RentalResponse(
                rental.getId(),
                rental.getStatus(),
                rental.getStartDate(),
                rental.getEndDate(),
                rental.getCustomer() != null ? rental.getCustomer().getName() : null,
                rental.getTools().stream().map(ToolResponse::from).toList()
        );
    }
}