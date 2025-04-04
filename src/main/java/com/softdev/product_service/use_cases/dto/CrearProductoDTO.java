package com.softdev.product_service.use_cases.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Esta clase representa un DTO (Data Transfer Object) para la creación de un
 * producto.
 */
@AllArgsConstructor
@Getter
public class CrearProductoDTO {

    /**
     * Nombre o descripción del producto.
     */
    private String nombre;
    /**
     * Precio del producto en la moneda establecida.
     */
    private Double precio;
    /**
     * Categoría a la que pertenece el producto.
     */
    private String categoria;
    /**
     * Cantidad disponible del producto en inventario.
     */
    private int stock;
}
