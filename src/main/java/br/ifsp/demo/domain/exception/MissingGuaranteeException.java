package br.ifsp.demo.domain.exception;

public class MissingGuaranteeException extends DomainException {
    public MissingGuaranteeException() {
        super("A guarantee is required to register a rental");

    }
}
