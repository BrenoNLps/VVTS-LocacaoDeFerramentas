package br.ifsp.demo.controller;

import br.ifsp.demo.controller.dto.ToolResponse;
import br.ifsp.demo.domain.repository.ToolRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tools")
@AllArgsConstructor
@Tag(name = "Tools")
public class ToolController {

    private final ToolRepository toolRepository;

    @Operation(summary = "List all tools")
    @GetMapping
    public ResponseEntity<List<ToolResponse>> listAll() {
        List<ToolResponse> tools = toolRepository.findAll().stream()
                .map(ToolResponse::from)
                .toList();
        return ResponseEntity.ok(tools);
    }
}