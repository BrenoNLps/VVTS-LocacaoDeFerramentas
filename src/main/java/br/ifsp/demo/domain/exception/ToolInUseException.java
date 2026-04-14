package br.ifsp.demo.domain.exception;

public class ToolInUseException extends DomainException {
    public ToolInUseException(String toolId) {
        super("Tool is in use: " + toolId);
    }
}
