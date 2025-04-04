package com.softdev.product_service.infrastructure.database.postgres.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
public class ProductoEntity {
    /**
     * Identificador único del producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public ProductoEntity(String nombre, Double precio, String categoria, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
    }

}
