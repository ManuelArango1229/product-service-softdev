package com.softdev.product_service.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa un producto en el sistema.
 * Contiene la información básica de un producto como su identificador,
 * nombre, precio, categoría y stock disponible.
 */
@AllArgsConstructor
@Data
public class Producto {
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
     * Cantidad disponible del producto en inventario.
     */
    private int stock;

    /**
     * Implementación del método toString para representar el objeto Producto como
     * una cadena.
     *
     * @return Cadena que representa el objeto Producto.
     */
    public String toString() {
        return "Producto{"
                + "id='" + id + '\''
                + ", nombre='" + nombre + '\''
                + ", precio=" + precio
                + ", categoria='" + categoria + '\''
                + ", stock=" + stock
                + '}';
    }
}
