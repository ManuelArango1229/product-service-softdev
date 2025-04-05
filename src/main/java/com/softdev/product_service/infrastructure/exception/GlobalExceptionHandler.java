package com.softdev.product_service.infrastructure.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.softdev.product_service.domain.exceptions.DatosInvalidosException;
import com.softdev.product_service.domain.exceptions.ProductoExisteException;

/**
 * Clase que maneja las excepciones globales de la aplicación.
 * Esta clase se encarga de capturar y manejar las excepciones lanzadas en el
 * controlador REST y devolver una respuesta adecuada al cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
        /**
     * Manejador de excepciones para la excepción UsuarioExisteErrorException.
     *
     * @param ex excepción lanzada
     * @return respuesta de error
     */
    @ExceptionHandler(ProductoExisteException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioExiste(final ProductoExisteException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    /**
     * Manejador de excepciones para la excepción DatosInvalidosException.
     *
     * @param ex excepción lanzada
     * @return respuesta de error
     */
    @ExceptionHandler(DatosInvalidosException.class)
    public ResponseEntity<?> handleDatosInvalidos(final DatosInvalidosException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

}
