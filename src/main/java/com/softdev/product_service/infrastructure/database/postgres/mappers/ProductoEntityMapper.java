package com.softdev.product_service.infrastructure.database.postgres.mappers;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.infrastructure.database.postgres.entities.ProductoEntity;

public class ProductoEntityMapper {
    public static ProductoEntity toEntity(Producto producto) {
        return new ProductoEntity(
                producto.getNombre(),
                producto.getPrecio(),
                producto.getCategoria(),
                producto.getStock());
    }

    public static Producto toDomain(ProductoEntity productoEntity) {
        return new Producto(
                productoEntity.getId(),
                productoEntity.getNombre(),
                productoEntity.getPrecio(),
                productoEntity.getCategoria(),
                productoEntity.getStock());
    }
}
