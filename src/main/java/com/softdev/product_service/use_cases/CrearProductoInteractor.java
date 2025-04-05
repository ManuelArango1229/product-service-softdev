package com.softdev.product_service.use_cases;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;
import com.softdev.product_service.use_cases.dto.CrearProductoDTO;
import com.softdev.product_service.domain.exceptions.DatosInvalidosException;
import com.softdev.product_service.domain.exceptions.ProductoExisteException;

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
        validarCampos(producto);

        Optional<Producto> createdProducto = productoRepository.findByNombre(producto.getNombre());
        if (createdProducto.isPresent()) {
            throw new ProductoExisteException("El producto con nombre '" + producto.getNombre() + "' ya existe.");
        }

        return productoRepository.save(new Producto(
            null,
            producto.getNombre(),
            producto.getPrecio(),
            producto.getCategoria(),
            producto.getMarca(),
            producto.getStock()
        ));
    }
    /**
     * Valida los campos del producto a crear.
     *
     * @param dto El DTO del producto a validar
     * @throws DatosInvalidosException si alguno de los campos no es válido
     */
    private void validarCampos(final CrearProductoDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            errores.add("El nombre no puede estar vacío");
        }

        if (dto.getPrecio() == null || dto.getPrecio() <= 0) {
            errores.add("El precio debe ser mayor a cero");
        }

        if (dto.getCategoria() == null || dto.getCategoria().trim().isEmpty()) {
            errores.add("La categoría no puede estar vacía");
        }

        if (dto.getMarca() == null || dto.getMarca().trim().isEmpty()) {
            errores.add("La marca no puede estar vacía");
        }

        if (dto.getStock() < 0) {
            errores.add("El stock no puede ser negativo");
        }
        if (!errores.isEmpty()) {
            throw new DatosInvalidosException(String.join(", ", errores));
        }
    }

}
