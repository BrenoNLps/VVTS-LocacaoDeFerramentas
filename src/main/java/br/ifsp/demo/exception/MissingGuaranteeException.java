package br.ifsp.demo.exception;

import br.ifsp.demo.domain.exception.DomainException;

public class MissingGuaranteeException extends DomainException {
    public MissingGuaranteeException() {
        super("A guarantee is required to register a rental");

    }
}
