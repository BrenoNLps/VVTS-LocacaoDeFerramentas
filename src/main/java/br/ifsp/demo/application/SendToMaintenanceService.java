package br.ifsp.demo.application;

import br.ifsp.demo.domain.MaintenanceRecord;

import java.util.Objects;
import java.util.UUID;

public class SendToMaintenanceService {
    public MaintenanceRecord execute(String toolId) {
        Objects.requireNonNull(toolId);
        return null;
    }
}
