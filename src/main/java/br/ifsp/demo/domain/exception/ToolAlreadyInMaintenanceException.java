package br.ifsp.demo.domain.exception;

public class ToolAlreadyInMaintenanceException extends RuntimeException {
    public ToolAlreadyInMaintenanceException(String toolId) {
        super("Tool is already in maintenance: " + toolId);
    }
}
