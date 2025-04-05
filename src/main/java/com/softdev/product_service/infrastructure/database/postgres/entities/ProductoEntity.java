package com.softdev.product_service.infrastructure.database.postgres.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un producto en la base de datos.
 */
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
    @Column(unique = true, nullable = false)
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
    private int stock;

    /**
     * Constructor para crear una entidad de producto con todos los atributos.
     *
     * @param nombreParam    Nombre del producto
     * @param precioParam    Precio del producto
     * @param categoriaParam Categoría del producto
     * @param marcaParam     Marca del producto
     * @param stockParam     Cantidad disponible del producto
     */
    public ProductoEntity(final String nombreParam, final Double precioParam, final String categoriaParam,
            final String marcaParam, final int stockParam) {
        this.nombre = nombreParam;
        this.precio = precioParam;
        this.categoria = categoriaParam;
        this.marca = marcaParam;
        this.stock = stockParam;
    }

}
