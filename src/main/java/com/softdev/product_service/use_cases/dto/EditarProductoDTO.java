package com.softdev.product_service.use_cases.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Esta clase representa un DTO (Data Transfer Object) para la edición de un
 * producto.
 */
@AllArgsConstructor
@Getter
public class EditarProductoDTO {

    /**
     * Identificador único del producto.
     */
    private Long id;

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
     * Marca del producto.
     */
    private String marca;

    /**
     * Cantidad disponible del producto en inventario.
     */
    private Integer stock;

    /**
     * Establece el ID del producto.
     *
     * @param productId ID del producto
     */
    public void setId(final Long productId) {
        this.id = productId;
    }

}
