package com.softdev.product_service.use_cases.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombre;
    /**
     * Precio del producto en la moneda establecida.
     */
    @NotNull(message = "El precio del producto no puede estar vacío")
    @Positive(message = "El precio del producto debe ser positivo")
    private Double precio;
    /**
     * Categoría a la que pertenece el producto.
     */
    @NotBlank(message = "La categoría del producto no puede estar vacía")
    private String categoria;
    /**
     * Marca del producto.
     */
    @NotBlank(message = "La marca del producto no puede estar vacía")
    private String marca;
    /**
     * Cantidad disponible del producto en inventario.
     */
    @NotNull(message = "El stock del producto no puede estar vacío")
    @Min(value = 0, message = "El stock debe ser igual o mayor a 0")
    private int stock;
}
