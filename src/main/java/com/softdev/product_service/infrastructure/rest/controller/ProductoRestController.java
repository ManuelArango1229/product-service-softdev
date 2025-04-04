package com.softdev.product_service.infrastructure.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.use_cases.CrearProductoInteractor;
import com.softdev.product_service.use_cases.dto.CrearProductoDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("registro")
@RestController
public class ProductoRestController {
    private final CrearProductoInteractor crearProductoInteractor;

    @RequestMapping("producto")
    public ResponseEntity<?> registrarProducto(@Valid @RequestBody Producto productoRequest) {
        try {
            CrearProductoDTO newProductoDTO = new CrearProductoDTO(
                    productoRequest.getNombre(),
                    productoRequest.getPrecio(),
                    productoRequest.getCategoria(),
                    productoRequest.getStock());
            crearProductoInteractor.execute(newProductoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el producto.");
        }
    }
}
