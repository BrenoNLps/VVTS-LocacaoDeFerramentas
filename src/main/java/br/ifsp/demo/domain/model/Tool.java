package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.exception.InvalidToolStateException;
import br.ifsp.demo.domain.exception.ToolAlreadyInMaintenanceException;
import br.ifsp.demo.domain.exception.ToolInUseException;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tool {
    @Getter String id;
    private String name;
    @Getter @Setter private ToolStatus status;
    private ProgressivePrices prices;
    private final List<MaintenanceRecord> maintenanceHistory;

    public Tool(String id, String name, ToolStatus status, ProgressivePrices prices) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.prices = prices;
        this.maintenanceHistory = new ArrayList<>();
    }

    public MaintenanceRecord sendToMaintenance(LocalDate date) {
        if(status == ToolStatus.RENTED) throw new ToolInUseException(this.id);
        if(status == ToolStatus.MAINTENANCE) throw new ToolAlreadyInMaintenanceException(this.id);
        status= ToolStatus.MAINTENANCE;
        MaintenanceRecord record = new MaintenanceRecord();
        this.maintenanceHistory.add(record);
        return record;
    }

    public MaintenanceRecord returnFromMaintenance(LocalDate date) {
        if (status == ToolStatus.AVAILABLE) {throw new InvalidToolStateException(id, status.name());}
        if (status == ToolStatus.RENTED) {throw new InvalidToolStateException(id, status.name());}
        status= ToolStatus.AVAILABLE;
        MaintenanceRecord record = new MaintenanceRecord();
        this.maintenanceHistory.add(record);
        return record;
    }
}
