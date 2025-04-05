package com.softdev.product_service.use_cases;

import org.springframework.stereotype.Service;
import com.softdev.product_service.domain.exceptions.ProductoNoEncontradoException;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;
import lombok.RequiredArgsConstructor;

/**
 * Caso de uso que implementa la lógica de negocio para eliminar un producto.
 * Esta clase sigue el patrón de diseño Interactor/Use Case de Clean Architecture.
 */
@Service
@RequiredArgsConstructor
public class EliminarProductoInteractor {

    /**
     * Puerto del repositorio de productos necesario para la persistencia.
     */
    private final ProductoRepositoryPort productoRepository;

    /**
     * Ejecuta la lógica de negocio para eliminar un producto por su ID.
     *
     * @param id El ID del producto a eliminar
     * @throws ProductoNoEncontradoException si el producto no existe
     */
    public void execute(Long id) {
        if (productoRepository.findById(id).isEmpty()) {
            throw new ProductoNoEncontradoException("Producto no encontrado con ID " + id);
        }

        productoRepository.deleteById(id);
    }
}
