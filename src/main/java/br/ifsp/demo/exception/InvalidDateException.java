package br.ifsp.demo.exception;

import br.ifsp.demo.domain.exception.DomainException;

public class InvalidDateException extends DomainException {
    public InvalidDateException(String field) {
        super("Invalid date for field: " + field);
    }
}
