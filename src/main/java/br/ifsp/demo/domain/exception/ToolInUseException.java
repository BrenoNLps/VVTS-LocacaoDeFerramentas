package br.ifsp.demo.domain.exception;

public class ToolInUseException extends RuntimeException {
    public ToolInUseException(String toolId) {
        super("Tool is in use: " + toolId);
    }
}
