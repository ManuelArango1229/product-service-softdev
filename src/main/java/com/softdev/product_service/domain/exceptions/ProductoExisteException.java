package com.softdev.product_service.domain.exceptions;

/**
 * Excepción que indica que un producto ya existe en el sistema.
 * Esta excepción se lanza cuando se intenta crear un producto con un identificador
 * que ya está en uso.
 */
public class ProductoExisteException extends RuntimeException {
    /**
     * Constructor que crea una nueva instancia de ProductoExisteException.
     *
     * @param message Mensaje de error que describe la excepción.
     */
    public ProductoExisteException(final String message) {
        super(message);
    }
}
