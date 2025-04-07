package com.softdev.product_service.domain.exceptions;

/**
 * Excepción que indica que un producto no fue encontrado en el sistema.
 * Esta excepción se lanza cuando se intenta acceder a un producto que no existe.
 */
public class ProductoNoEncontradoException extends RuntimeException {
    /**
     * Constructor que crea una nueva instancia de ProductoNoEncontradoException.
     *
     * @param message Mensaje de error que describe la excepción.
     */
    public ProductoNoEncontradoException(final String message) {
        super(message);
    }
}
