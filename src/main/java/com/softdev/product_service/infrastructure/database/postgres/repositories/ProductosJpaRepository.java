package com.softdev.product_service.infrastructure.database.postgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softdev.product_service.infrastructure.database.postgres.entities.ProductoEntity;

/**
 * Interfaz que extiende JpaRepository para realizar operaciones CRUD en la base
 * de datos.
 */
public interface ProductosJpaRepository extends JpaRepository<ProductoEntity, Long> {
    /**
     * Método para buscar un producto por su nombre.
     *
     * @param nombre el nombre del producto a buscar
     * @return un Optional que contiene el producto si se encuentra, o vacío si no
     */
    ProductoEntity findByNombre(String nombre);
}
