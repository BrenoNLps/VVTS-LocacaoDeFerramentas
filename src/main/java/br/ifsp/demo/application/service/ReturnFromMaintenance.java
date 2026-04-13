package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.MaintenanceRecord;

public class ReturnFromMaintenance {

    public MaintenanceRecord execute(String toolId) {
        if(toolId==null) throw new NullPointerException();
        return null;
    }

}
