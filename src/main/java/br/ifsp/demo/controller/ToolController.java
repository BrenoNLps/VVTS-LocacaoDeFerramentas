package br.ifsp.demo.controller;

import br.ifsp.demo.application.service.CreateTool;
import br.ifsp.demo.application.service.ListTools;
import br.ifsp.demo.controller.dto.CreateToolRequest;
import br.ifsp.demo.controller.dto.ToolResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tools")
@AllArgsConstructor
@Tag(name = "Tools")
public class ToolController {

    private final ListTools listTools;
    private final CreateTool createTool;

    @Operation(summary = "List all tools")
    @GetMapping
    public ResponseEntity<List<ToolResponse>> listAll() {
        List<ToolResponse> tools = listTools.execute().stream()
                .map(ToolResponse::from)
                .toList();
        return ResponseEntity.ok(tools);
    }

    @Operation(summary = "Create a new tool")
    @PostMapping
    public ResponseEntity<ToolResponse> create(@RequestBody CreateToolRequest request) {
        var tool = createTool.execute(
                request.name(),
                request.dailyRate(),
                request.weeklyDailyRate(),
                request.monthlyDailyRate()
        );
        return ResponseEntity.created(URI.create("/api/v1/tools/" + tool.getId()))
                .body(ToolResponse.from(tool));
    }
}
