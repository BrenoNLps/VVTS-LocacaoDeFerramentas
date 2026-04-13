package br.ifsp.demo.application;

import br.ifsp.demo.domain.MaintenanceRecord;
import br.ifsp.demo.domain.Tool;
import br.ifsp.demo.domain.repository.ToolRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;

public class SendToMaintenanceService {
    private final ToolRepository toolRepository;

    public SendToMaintenanceService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public MaintenanceRecord execute(String toolId) {
        Objects.requireNonNull(toolId);
        if(toolId.isBlank()) throw new IllegalArgumentException();
        Tool tool = toolRepository.findById(toolId);
        if(tool == null) throw new EntityNotFoundException();
        return null;
    }
}
