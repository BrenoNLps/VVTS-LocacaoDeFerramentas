package br.ifsp.demo.domain.exception;

public class InvalidDateException extends DomainException {
    public InvalidDateException(String field) {
        super("Invalid date for field: " + field);
    }
}
