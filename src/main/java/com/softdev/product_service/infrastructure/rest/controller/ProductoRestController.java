package com.softdev.product_service.infrastructure.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.use_cases.CrearProductoInteractor;
import com.softdev.product_service.use_cases.dto.CrearProductoDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST que maneja las operaciones relacionadas con los productos.
 * Este controlador se encarga de exponer los endpoints para la gestión de
 * productos
 * en la aplicación.
 */
@RequiredArgsConstructor
@RequestMapping("registro")
@RestController
public class ProductoRestController {
    /**
     * Interactor encargado de la lógica de negocio para crear productos.
     */
    private final CrearProductoInteractor crearProductoInteractor;

    /**
     * Endpoint para registrar un nuevo producto en el sistema.
     *
     * @param productoRequest El producto a registrar con sus datos validados
     * @return ResponseEntity con el estado de la operación:
     *         - HttpStatus.CREATED y mensaje de éxito si se registra correctamente
     *         - HttpStatus.INTERNAL_SERVER_ERROR y mensaje de error en caso de
     *         fallo
     */
    @PostMapping("producto")
    public ResponseEntity<?> registrarProducto(@Valid @RequestBody final Producto productoRequest) {
        CrearProductoDTO newProductoDTO = new CrearProductoDTO(
            productoRequest.getNombre(),
            productoRequest.getPrecio(),
            productoRequest.getCategoria(),
            productoRequest.getMarca(),
            productoRequest.getStock());

        crearProductoInteractor.execute(newProductoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto registrado exitosamente.");
    }
}
