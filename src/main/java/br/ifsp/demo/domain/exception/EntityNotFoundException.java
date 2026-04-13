package br.ifsp.demo.domain.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String id) {
        super("Entity not found with id: " + id);
    }
}
