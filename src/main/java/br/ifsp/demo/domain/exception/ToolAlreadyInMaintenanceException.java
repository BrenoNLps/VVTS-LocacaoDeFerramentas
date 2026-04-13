package br.ifsp.demo.domain.exception;

public class ToolAlreadyInMaintenanceException extends RuntimeException {
    public ToolAlreadyInMaintenanceException(String message) {
        super(message);
    }
}
