package com.softdev.product_service.infrastructure.database.postgres.adapters;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
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
     * Busca productos por nombre, categoría y/o marca (filtros opcionales).
     *
     * @param nombre    parte del nombre (puede ser null o vacío)
     * @param categoria categoría del producto (puede ser null o vacío)
     * @param marca     marca del producto (puede ser null o vacío)
     * @return lista de productos que coincidan con los filtros
     */
    @Override
    public List<Producto> buscarPorFiltros(final String nombre, final String categoria, final String marca) {
        String nombreVal = (nombre != null && !nombre.trim().isEmpty()) ? "%" + nombre + "%" : null;
        String categoriaVal = (categoria != null && !categoria.trim().isEmpty()) ? "%" + categoria + "%" : null;
        String marcaVal = (marca != null && !marca.trim().isEmpty()) ? "%" + marca + "%" : null;
        return productosJpaRepository
                .buscarPorFiltros(nombreVal, categoriaVal, marcaVal)
                .stream()
                .map(ProductoEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
    /**
     * Busca un producto por su identificador.
     *
     * @param id El identificador del producto a buscar
     * @return El producto encontrado
     * @throws RuntimeException si el producto no existe
     */
    @Override
    public Optional<Producto> findById(final Long id) {
        return Optional.ofNullable(productosJpaRepository.findById(id))
                .map(productoEntity -> ProductoEntityMapper.toDomain(productoEntity.get()));
    }

    /**
     * Busca un producto por su nombre.
     *
     * @param nombre El nombre del producto a buscar
     * @return El producto encontrado
     * @throws RuntimeException si el producto no existe
     */
    @Override
    public Optional<Producto> findByNombre(final String nombre) {
        Optional<Producto> entiedad = Optional.ofNullable(productosJpaRepository.findByNombre(nombre)).map(
                productoEntity -> ProductoEntityMapper.toDomain(productoEntity));
        return entiedad;
    }

    /**
     * Busca un producto por su nombre y un ID diferente.
     *
     * @param nombre El nombre del producto a buscar
     * @param id     El ID del producto a excluir de la búsqueda
     * @return El producto encontrado
     * @throws RuntimeException si el producto no existe
     */
    @Override
    public Optional<Producto> findByNombreAndIdNot(final String nombre, final Long id) {
        return Optional.ofNullable(productosJpaRepository.findByNombreAndIdNot(nombre, id)).map(
                productoEntity -> ProductoEntityMapper.toDomain(productoEntity));
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param id El identificador del producto a eliminar
     */
    @Override
    public void deleteById(final Long id) {
        productosJpaRepository.deleteById(id);
    }
}
