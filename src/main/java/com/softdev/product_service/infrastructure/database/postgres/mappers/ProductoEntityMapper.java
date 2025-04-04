package com.softdev.product_service.infrastructure.database.postgres.mappers;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.infrastructure.database.postgres.entities.ProductoEntity;

/**
 * Clase utilitaria que proporciona métodos de mapeo entre las entidades de
 * dominio (Producto)
 * y las entidades de persistencia (ProductoEntity).
 * Esta clase implementa el patrón Mapper para mantener la separación entre las
 * capas de dominio
 * e infraestructura.
 */
public class ProductoEntityMapper {
    /**
     * Convierte una entidad de dominio Producto a una entidad de persistencia
     * ProductoEntity.
     *
     * @param producto La entidad de dominio a convertir
     * @return Una nueva instancia de ProductoEntity con los datos del producto
     */
    public static ProductoEntity toEntity(final Producto producto) {
        return new ProductoEntity(
                producto.getNombre(),
                producto.getPrecio(),
                producto.getCategoria(),
                producto.getStock());
    }

    /**
     * Convierte una entidad de persistencia ProductoEntity a una entidad de dominio
     * Producto.
     *
     * @param productoEntity La entidad de persistencia a convertir
     * @return Una nueva instancia de Producto con los datos de la entidad
     */
    public static Producto toDomain(final ProductoEntity productoEntity) {
        return new Producto(
                productoEntity.getId(),
                productoEntity.getNombre(),
                productoEntity.getPrecio(),
                productoEntity.getCategoria(),
                productoEntity.getStock());
    }
}
