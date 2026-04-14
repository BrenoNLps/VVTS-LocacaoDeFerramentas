package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.model.MaintenanceRecord;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.repository.ToolRepository;

import java.util.Objects;

public class ReturnFromMaintenance {
    private final ToolRepository toolRepository;

    public ReturnFromMaintenance(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public MaintenanceRecord execute(String toolId) {
        Objects.requireNonNull(toolId);
        if(toolId.isBlank()) throw new InvalidArgumentException("ToolId");
        Tool tool = toolRepository.findById(toolId);
        if(tool==null) throw new EntityNotFoundException(toolId);
        return null;
    }

}
