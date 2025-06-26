package com.softdev.product_service.infrastructure.database.postgres.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * Método para buscar un producto por su nombre y un ID diferente.
     *
     * @param nombre el nombre del producto a buscar
     * @param id     el ID del producto a excluir de la búsqueda
     * @return un Optional que contiene el producto si se encuentra, o vacío si no
     */
    ProductoEntity findByNombreAndIdNot(String nombre, Long id);

        /**
     * Búsqueda dinámica por nombre, categoría y/o marca (ignorando mayúsculas).
     *
     * @param nombre nombre parcial (puede ser null)
     * @param categoria categoría del producto (puede ser null)
     * @param marca marca del producto (puede ser null)
     * @return lista de productos que coincidan con los filtros
     */
    @Query("""
        SELECT p FROM ProductoEntity p
        WHERE (COALESCE(:nombre, '') = '' OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
          AND (COALESCE(:categoria, '') = '' OR LOWER(p.categoria) LIKE LOWER(CONCAT('%', :categoria, '%')))
          AND (COALESCE(:marca, '') = '' OR LOWER(p.marca) LIKE LOWER(CONCAT('%', :marca, '%')))
    """)
    List<ProductoEntity> buscarPorFiltros(
        @Param("nombre") String nombre,
        @Param("categoria") String categoria,
        @Param("marca") String marca
    );

}
