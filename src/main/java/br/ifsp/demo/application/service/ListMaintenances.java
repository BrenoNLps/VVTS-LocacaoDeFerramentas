package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMaintenances {

    private final ToolRepository toolRepository;

    public ListMaintenances(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public List<Tool> execute() {
        return toolRepository.findAll();
    }
}