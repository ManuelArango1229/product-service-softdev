package com.softdev.product_service.use_cases;

import org.springframework.stereotype.Component;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;
import com.softdev.product_service.use_cases.dto.CrearProductoDTO;

/**
 * Caso de uso que implementa la lógica de negocio para crear un nuevo producto.
 * Esta clase sigue el patrón de diseño Interactor/Use Case de Clean
 * Architecture.
 */
@Component
public class CrearProductoInteractor {
    /**
     * Puerto del repositorio de productos necesario para la persistencia.
     */
    private final ProductoRepositoryPort productoRepository;

    /**
     * Constructor que recibe el puerto del repositorio de productos.
     *
     * @param productoRepositoryParam Implementación del puerto del repositorio de
     *                                productos
     */
    public CrearProductoInteractor(final ProductoRepositoryPort productoRepositoryParam) {
        this.productoRepository = productoRepositoryParam;
    }

    /**
     * Ejecuta la lógica de negocio para crear un nuevo producto.
     *
     * @param producto El producto a crear
     * @return El producto creado con su identificador asignado
     */
    public Producto execute(final CrearProductoDTO producto) {
        Producto createdProducto = productoRepository.findByNombre(producto.getNombre());
        if (createdProducto != null) {
            throw new IllegalArgumentException("El producto ya existe.");
        }
        return productoRepository.save(new Producto(
                null,
                producto.getNombre(),
                producto.getPrecio(),
                producto.getCategoria(),
                producto.getStock()));
    }
}
