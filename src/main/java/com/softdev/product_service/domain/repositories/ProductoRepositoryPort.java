package com.softdev.product_service.domain.repositories;

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
    Producto findById(String id);
}
