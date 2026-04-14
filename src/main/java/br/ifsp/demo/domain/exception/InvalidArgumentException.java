package br.ifsp.demo.domain.exception;

public class InvalidArgumentException extends DomainException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
