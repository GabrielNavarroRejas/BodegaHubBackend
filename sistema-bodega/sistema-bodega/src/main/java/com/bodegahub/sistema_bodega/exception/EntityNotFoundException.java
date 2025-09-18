package com.bodegahub.sistema_bodega.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " con ID " + id + " no encontrado");
    }
}
