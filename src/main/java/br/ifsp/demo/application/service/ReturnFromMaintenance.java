package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.model.MaintenanceRecord;

public class ReturnFromMaintenance {

    public MaintenanceRecord execute(String toolId) {
        if(toolId==null) throw new NullPointerException();
        if(toolId.isBlank()) throw new InvalidArgumentException("ToolId");
        return null;
    }

}
