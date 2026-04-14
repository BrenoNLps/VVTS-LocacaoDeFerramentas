package br.ifsp.demo.domain.model;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

public class MaintenanceRecord {
    @Getter private final String id;
    @Getter private final LocalDate sentDate;
    @Getter private LocalDate returnDate;
    private boolean closed;

    public MaintenanceRecord(LocalDate sentDate) {
        this.id = UUID.randomUUID().toString();
        this.sentDate = sentDate;
        this.closed = false;
    }

    public void close(LocalDate returnDate) {

    }

    public boolean isOpen() {
        return false;
    }
}