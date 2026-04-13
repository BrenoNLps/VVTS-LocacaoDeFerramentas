package br.ifsp.demo.domain.model;

import java.time.LocalDate;

public class MaintenanceRecord {
    private String id;
    private LocalDate sentDate;
    private LocalDate returnDate;
    private boolean closed;

    public void close(LocalDate returnDate) {

    }

    public boolean isOpen() {
        return false;
    }
}