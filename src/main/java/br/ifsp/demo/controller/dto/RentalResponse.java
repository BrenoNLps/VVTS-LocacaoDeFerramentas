package br.ifsp.demo.controller.dto;

import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.model.RentalStatus;

import java.time.LocalDate;
import java.util.List;

public record RentalResponse(
        String id,
        RentalStatus status,
        LocalDate startDate,
        List<ToolResponse> tools
) {
    public static RentalResponse from(Rental rental) {
        return new RentalResponse(
                rental.getId(),
                rental.getStatus(),
                rental.getStartDate(),
                rental.getTools().stream().map(ToolResponse::from).toList()
        );
    }
}