package com.softdev.product_service.domain.repositories;

import java.util.Optional;
import java.util.List;
import com.softdev.product_service.domain.entities.Producto;

public interface ProductoRepositoryPort {
    /**
    * Busca productos por nombre o categoría.
    *
    * @param nombre    nombre del producto
    * @param categoria categoría del producto
    * @param marca     marca del producto
    * @return lista de productos que coinciden con los parámetros
    */
    List<Producto> buscarPorFiltros(String nombre, String categoria, String marca);

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

    /**
     * Método para buscar un producto por su nombre y un ID diferente en el repositorio.
     *
     * @param nombre El nombre del producto a buscar.
     * @param id     El ID del producto a excluir de la búsqueda.
     * @return El producto encontrado o null si no existe.
     */
    Optional<Producto> findByNombreAndIdNot(String nombre, Long id);

    /**
     * Método para eliminar un producto por su ID en el repositorio.
     *
     * @param id El ID del producto a eliminar.
     */
    void deleteById(Long id);
}
