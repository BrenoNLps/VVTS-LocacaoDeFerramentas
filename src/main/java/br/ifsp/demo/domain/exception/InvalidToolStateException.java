package br.ifsp.demo.domain.exception;

public class InvalidToolStateException extends DomainException {
    public InvalidToolStateException(String toolId, String state) {
        super("Tool " + toolId + " is in invalid state for this operation: " + state);
    }
}
