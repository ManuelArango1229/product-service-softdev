package com.softdev.product_service.infrastructure.database.postgres.adapters;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;
import com.softdev.product_service.infrastructure.database.postgres.entities.ProductoEntity;
import com.softdev.product_service.infrastructure.database.postgres.mappers.ProductoEntityMapper;
import com.softdev.product_service.infrastructure.database.postgres.repositories.ProductosJpaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {

    private final ProductosJpaRepository productosJpaRepository;

    @Override
    public Producto save(Producto producto) {
        ProductoEntity productoEntity = ProductoEntityMapper.toEntity(producto);
        Producto productoSaved = ProductoEntityMapper.toDomain(productosJpaRepository.save(productoEntity));
        return productoSaved;
    }

    @Override
    public Producto findById(String id) {
        return ProductoEntityMapper.toDomain(
                productosJpaRepository.findById(Long.valueOf(id))
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
    }

    @Override
    public Producto findByNombre(String nombre) {
        return ProductoEntityMapper.toDomain(
                productosJpaRepository.findByName(nombre)
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
    }

}
