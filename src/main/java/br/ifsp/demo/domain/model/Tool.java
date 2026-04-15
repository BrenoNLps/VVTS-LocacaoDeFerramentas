package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.exception.InvalidToolStateException;
import br.ifsp.demo.domain.exception.ToolAlreadyInMaintenanceException;
import br.ifsp.demo.domain.exception.ToolInUseException;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.ifsp.demo.domain.model.ToolStatus.*;

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
        if(status == RENTED) throw new ToolInUseException(this.id);
        if(status == MAINTENANCE) throw new ToolAlreadyInMaintenanceException(this.id);
        status= MAINTENANCE;
        MaintenanceRecord record = new MaintenanceRecord();
        this.maintenanceHistory.add(record);
        return record;
    }

    public MaintenanceRecord returnFromMaintenance(LocalDate date) {
        if (status == AVAILABLE) {throw new InvalidToolStateException(id, status.name());}
        if (status == RENTED) {throw new InvalidToolStateException(id, status.name());}
        status= AVAILABLE;
        MaintenanceRecord record = new MaintenanceRecord();
        this.maintenanceHistory.add(record);
        return record;
    }

    public boolean isAvailable(){
        return status == AVAILABLE;
    }

    public void markAsRented(){
        this.status = RENTED;
    }

    public void markAsAvailable() {
        this.status = AVAILABLE;
    }
}
