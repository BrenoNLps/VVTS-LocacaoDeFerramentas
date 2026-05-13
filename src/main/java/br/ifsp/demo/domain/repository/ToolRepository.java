package br.ifsp.demo.domain.repository;

import br.ifsp.demo.domain.model.Tool;

import java.util.List;

public interface ToolRepository {
    void save(Tool tool);
    Tool findById(String id);
    List<Tool> findAll();
}
