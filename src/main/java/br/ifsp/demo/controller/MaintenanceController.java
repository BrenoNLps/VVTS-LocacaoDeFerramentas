package br.ifsp.demo.controller;

import br.ifsp.demo.application.service.*;
import br.ifsp.demo.controller.dto.MaintenanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenance")
@AllArgsConstructor
@Tag(name = "Maintenance")
public class MaintenanceController {
    private final SendToMaintenance sendToMaintenance;
    private final ReturnFromMaintenance returnFromMaintenance;
    private final ListMaintenances listMaintenances;

    @Operation(summary = "List all maintenance records")
    @GetMapping
    public ResponseEntity<List<MaintenanceResponse>> listAll() {
        List<MaintenanceResponse> records = listMaintenances.execute().stream()
                .flatMap(tool -> tool.getMaintenanceHistory().stream()
                        .map(record -> MaintenanceResponse.from(tool.getId(), tool.getName(), record)))
                .toList();
        return ResponseEntity.ok(records);
    }

    @Operation(summary = "Send a tool to maintenance")
    @PostMapping("/send/{toolId}")
    public ResponseEntity<Void> send(@PathVariable String toolId) {
        sendToMaintenance.execute(toolId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Return a tool from maintenance")
    @PutMapping("/return/{toolId}")
    public ResponseEntity<Void> returnTool(@PathVariable String toolId) {
        returnFromMaintenance.execute(toolId);
        return ResponseEntity.ok().build();
    }
}