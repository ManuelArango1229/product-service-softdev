package com.softdev.product_service.use_cases;

import org.springframework.stereotype.Service;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;

/**
 * Caso de uso para actualizar el stock de un producto.
 * Esta clase sigue el patrón de diseño Interactor/Use Case de Clean Architecture.
 */
@Service
public class ActualizarStockInteractor {

    /**
     * Puerto del repositorio de productos necesario para la persistencia.
     */
    private final ProductoRepositoryPort productoRepositoryPort;

    /**
     * Constructor que recibe el puerto del repositorio de productos.
     *
     * @param repositoryPort Implementación del puerto del repositorio de
     *                                productos
     */
    public ActualizarStockInteractor(final ProductoRepositoryPort repositoryPort) {
        this.productoRepositoryPort = repositoryPort;
    }

    /**
     * Ejecuta la lógica de negocio para actualizar el stock de un producto.
     *
     * @param nombre   Nombre del producto a actualizar
     * @param cantidad Cantidad a agregar o restar al stock
     * @throws IllegalArgumentException si el stock es insuficiente
     */
    public void actualizarStock(final String nombre, final int cantidad) {
        Producto producto = productoRepositoryPort.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente");
        }
        producto.setStock(producto.getStock() - cantidad);
        productoRepositoryPort.save(producto);
    }
}
