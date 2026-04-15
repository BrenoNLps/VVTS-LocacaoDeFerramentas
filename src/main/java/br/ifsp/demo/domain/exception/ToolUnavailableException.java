package br.ifsp.demo.domain.exception;

public class ToolUnavailableException extends DomainException {
    public ToolUnavailableException(String toolId) {
        super("Tool is not available: " + toolId);
    }
}
