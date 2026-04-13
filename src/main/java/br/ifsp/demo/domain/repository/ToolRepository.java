package br.ifsp.demo.domain.repository;

import br.ifsp.demo.domain.model.Tool;

public interface ToolRepository {
    void save(Tool tool);
    Tool findById(String id);
}
