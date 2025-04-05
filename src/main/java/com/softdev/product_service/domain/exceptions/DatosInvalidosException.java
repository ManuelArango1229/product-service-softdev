package com.softdev.product_service.domain.exceptions;

/**
 * Excepción que indica que los datos proporcionados son inválidos.
 * Esta excepción se lanza cuando se intenta crear o actualizar un producto con
 * datos que no cumplen con los requisitos del sistema.
 */
public class DatosInvalidosException extends RuntimeException {
    /**
     * Constructor que crea una nueva instancia de DatosInvalidosException.
     *
     * @param message Mensaje de error que describe la excepción.
     */
    public DatosInvalidosException(final String message) {
        super(message);
    }
}

