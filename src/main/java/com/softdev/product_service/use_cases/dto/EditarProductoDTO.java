package com.softdev.product_service.use_cases.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull(message = "El ID del producto es obligatorio")
    private Long id;

    /**
     * Nombre o descripción del producto.
     */
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombre;

    /**
     * Precio del producto en la moneda establecida.
     */
    @NotNull(message = "El precio del producto no puede estar vacío")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precio;

    /**
     * Categoría a la que pertenece el producto.
     */
    @NotBlank(message = "La categoría no puede estar vacía")
    private String categoria;

    /**
     * Marca del producto.
     */
    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;

    /**
     * Cantidad disponible del producto en inventario.
     */
    @NotNull(message = "El stock no puede estar vacío")
    @Positive(message = "El stock debe ser positivo")
    private int stock;
}
