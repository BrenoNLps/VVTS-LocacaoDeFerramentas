package br.ifsp.demo.domain.exception;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String entity, String id) {
        super(entity + " not found with id: " + id);
    }
}
