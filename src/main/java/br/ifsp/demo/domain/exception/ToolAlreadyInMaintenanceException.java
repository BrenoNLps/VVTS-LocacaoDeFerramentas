package br.ifsp.demo.domain.exception;

public class ToolAlreadyInMaintenanceException extends DomainException {
    public ToolAlreadyInMaintenanceException(String toolId) {
        super("Tool is already in maintenance: " + toolId);
    }
}
