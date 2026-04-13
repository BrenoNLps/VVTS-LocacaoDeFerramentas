package br.ifsp.demo.domain;

import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tool {
    private String id;
    private String name;
    @Setter private ToolStatus status;
    private ProgressivePrices prices;
    private final List<MaintenanceRecord> maintenanceHistory;

    public Tool() {
        this.maintenanceHistory = new ArrayList<>();
    }

    public MaintenanceRecord sendToMaintenance(LocalDate date) {
        status= ToolStatus.MAINTENANCE;
        MaintenanceRecord record = new MaintenanceRecord();
        this.maintenanceHistory.add(record);
        return record;
    }
}
