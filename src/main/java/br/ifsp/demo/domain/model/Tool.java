package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.exception.InvalidToolStateException;
import br.ifsp.demo.domain.exception.ToolAlreadyInMaintenanceException;
import br.ifsp.demo.domain.exception.ToolInUseException;
import lombok.Getter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.ifsp.demo.domain.model.ToolStatus.*;

public class Tool {
    @Getter private final String id;
    @Getter private final String name;
    @Getter private ToolStatus status;
    @Getter private final ProgressivePrices prices;
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
        MaintenanceRecord record = new MaintenanceRecord(date);
        this.maintenanceHistory.add(record);
        return record;
    }

    public MaintenanceRecord returnFromMaintenance(LocalDate date) {
        if (status == ToolStatus.AVAILABLE) throw new InvalidToolStateException(id, status.name());
        if (status == ToolStatus.RENTED) throw new InvalidToolStateException(id, status.name());
        MaintenanceRecord record = maintenanceHistory.stream()
                .filter(MaintenanceRecord::isOpen)
                .findFirst()
                .orElseThrow(() -> new InvalidToolStateException(id, status.name()));
        status = ToolStatus.AVAILABLE;
        record.close(date);
        return record;
    }

    public List<MaintenanceRecord> getMaintenanceHistory() {
        return Collections.unmodifiableList(maintenanceHistory);
    }

    public boolean isAvailable() {return status == ToolStatus.AVAILABLE;}
    public void markAsAvailable() {this.status = ToolStatus.AVAILABLE;}
    public void markAsRented() {this.status = ToolStatus.RENTED;}

}
