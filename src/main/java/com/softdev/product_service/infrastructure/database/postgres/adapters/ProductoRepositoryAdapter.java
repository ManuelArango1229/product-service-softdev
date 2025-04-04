package com.softdev.product_service.infrastructure.database.postgres.adapters;

import org.springframework.stereotype.Repository;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;
import com.softdev.product_service.infrastructure.database.postgres.entities.ProductoEntity;
import com.softdev.product_service.infrastructure.database.postgres.mappers.ProductoEntityMapper;
import com.softdev.product_service.infrastructure.database.postgres.repositories.ProductosJpaRepository;

import lombok.AllArgsConstructor;

/**
 * Adaptador que implementa el puerto del repositorio de productos para
 * PostgreSQL.
 * Esta clase actúa como un puente entre el dominio y la infraestructura de
 * persistencia,
 * siguiendo el patrón Ports and Adapters (Arquitectura Hexagonal).
 */
@AllArgsConstructor
@Repository
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {
    /**
     * Repositorio JPA para realizar operaciones de persistencia en PostgreSQL.
     */
    private final ProductosJpaRepository productosJpaRepository;

    /**
     * Guarda un producto en la base de datos.
     *
     * @param producto El producto del dominio a persistir
     * @return El producto persistido con su identificador asignado
     */
    @Override
    public Producto save(final Producto producto) {
        ProductoEntity productoEntity = ProductoEntityMapper.toEntity(producto);
        Producto productoSaved = ProductoEntityMapper.toDomain(productosJpaRepository.save(productoEntity));
        return productoSaved;
    }

    /**
     * Busca un producto por su identificador.
     *
     * @param id El identificador del producto a buscar
     * @return El producto encontrado
     * @throws RuntimeException si el producto no existe
     */
    @Override
    public Producto findById(final String id) {
        return ProductoEntityMapper.toDomain(
                productosJpaRepository.findById(Long.valueOf(id))
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
    }

    /**
     * Busca un producto por su nombre.
     *
     * @param nombre El nombre del producto a buscar
     * @return El producto encontrado
     * @throws RuntimeException si el producto no existe
     */
    @Override
    public Producto findByNombre(final String nombre) {
        return ProductoEntityMapper.toDomain(
                productosJpaRepository.findByName(nombre)
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
    }

}
