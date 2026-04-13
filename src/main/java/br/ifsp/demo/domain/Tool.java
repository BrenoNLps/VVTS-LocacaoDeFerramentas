package br.ifsp.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Tool {
    private String id;
    private String name;
    private ToolStatus status;
    private ProgressivePrices prices;
    private final List<MaintenanceRecord> maintenanceRecords;

    public Tool() {
        this.maintenanceRecords = new ArrayList<>();
    }
}
