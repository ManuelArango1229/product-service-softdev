package com.softdev.product_service.domain.repositories;

import java.util.Optional;

import com.softdev.product_service.domain.entities.Producto;

public interface ProductoRepositoryPort {
    /**
     * Método para guardar un producto en el repositorio.
     *
     * @param producto El producto a guardar.
     * @return El producto guardado.
     */
    Producto save(Producto producto);

    /**
     * Método para buscar un producto por su ID en el repositorio.
     *
     * @param id El ID del producto a buscar.
     * @return El producto encontrado o null si no existe.
     */
    Optional<Producto> findById(Long id);

    /**
     * Método para buscar un producto por su nombre en el repositorio.
     *
     * @param nombre El nombre del producto a buscar.
     * @return El producto encontrado o null si no existe.
     */
    Optional<Producto> findByNombre(String nombre);
}
