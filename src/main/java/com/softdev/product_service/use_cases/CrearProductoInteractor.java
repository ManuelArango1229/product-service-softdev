package com.softdev.product_service.use_cases;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;

public class CrearProductoInteractor {
    private final ProductoRepositoryPort productoRepository;

    public CrearProductoInteractor(ProductoRepositoryPort productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto execute(Producto producto) {
        return productoRepository.save(producto);
    }
}
