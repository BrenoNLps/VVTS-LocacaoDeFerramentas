package br.ifsp.demo.controller.dto;

import br.ifsp.demo.domain.model.MaintenanceRecord;

import java.time.LocalDate;

public record MaintenanceResponse(
        String id,
        String toolId,
        String toolName,
        LocalDate sentDate,
        LocalDate returnDate,
        boolean closed
) {
    public static MaintenanceResponse from(String toolId, String toolName, MaintenanceRecord record) {
        return new MaintenanceResponse(
                record.getId(),
                toolId,
                toolName,
                record.getSentDate(),
                record.getReturnDate(),
                record.isClosed()
        );
    }
}