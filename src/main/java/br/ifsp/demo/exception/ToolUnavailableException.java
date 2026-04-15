package br.ifsp.demo.exception;

import br.ifsp.demo.domain.exception.DomainException;

public class ToolUnavailableException extends DomainException {
    public ToolUnavailableException(String toolId) {
        super("Tool is not available: " + toolId);
    }
}
