package br.ifsp.demo.domain.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, String id) {
        super(entity + " not found with id: " + id);
    }
}
