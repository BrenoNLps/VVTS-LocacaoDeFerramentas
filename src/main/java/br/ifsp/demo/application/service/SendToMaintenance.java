package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.MaintenanceRecord;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.repository.ToolRepository;

import java.time.LocalDate;
import java.util.Objects;

public class SendToMaintenance {
    private final ToolRepository toolRepository;

    public SendToMaintenance(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public MaintenanceRecord execute(String toolId) {
        Objects.requireNonNull(toolId);
        if(toolId.isBlank()) throw new InvalidArgumentException("ToolId");
        Tool tool = toolRepository.findById(toolId);
        if(tool == null) throw new EntityNotFoundException(toolId);
        toolRepository.save(tool);
        return tool.sendToMaintenance(LocalDate.now());
    }
}
