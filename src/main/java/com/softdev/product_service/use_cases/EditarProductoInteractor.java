package com.softdev.product_service.use_cases;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.exceptions.DatosInvalidosException;
import com.softdev.product_service.domain.exceptions.ProductoNoEncontradoException;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;
import com.softdev.product_service.use_cases.dto.EditarProductoDTO;

/**
 * Interactor para editar un producto existente en el sistema.
 * Este interactor se encarga de la lógica de negocio relacionada con la edición de
 * productos.
 */
@Component
public class EditarProductoInteractor {

    /**
     * Puerto del repositorio de productos necesario para la persistencia.
     */
    private final ProductoRepositoryPort productoRepository;

    /**
     * Constructor que recibe el puerto del repositorio de productos.
     *
     * @param productoRepositoryPort Implementación del puerto del repositorio de
     *                           productos
     */
    public EditarProductoInteractor(final ProductoRepositoryPort productoRepositoryPort) {
        this.productoRepository = productoRepositoryPort;
    }
    /**
     * Ejecuta la lógica de negocio para editar un producto existente.
     *
     * @param dto Objeto que contiene los datos del producto a editar
     * @return El producto editado
     */
    public Producto execute(final EditarProductoDTO dto) {
        validarCampos(dto);

        Producto productoExistente = productoRepository.findById(dto.getId())
            .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID " + dto.getId()));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            if (productoRepository.findByNombre(dto.getNombre()).isPresent()) {
                throw new DatosInvalidosException("El nombre del producto ya existe.");
            }
            productoExistente.setNombre(dto.getNombre());
        }
        if (dto.getNombre() != null) {
            productoExistente.setNombre(dto.getNombre());
        }
        if (dto.getPrecio() != null) {
            productoExistente.setPrecio(dto.getPrecio());
        }
        if (dto.getCategoria() != null) {
            productoExistente.setCategoria(dto.getCategoria());
        }
        if (dto.getMarca() != null) {
            productoExistente.setMarca(dto.getMarca());
        }
        if (dto.getStock() != null) {
            productoExistente.setStock(dto.getStock());
        }

        return productoRepository.save(productoExistente);
    }

    /**
     * Valida los campos del producto a editar.
     *
     * @param dto Objeto que contiene los datos del producto a editar
     * @throws DatosInvalidosException si alguno de los campos es inválido
     */
    private void validarCampos(final EditarProductoDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto.getNombre() != null && dto.getNombre().isBlank()) {
            errores.add("El nombre no puede estar vacío");
        }
        if (dto.getPrecio() != null && dto.getPrecio() <= 0) {
            errores.add("El precio debe ser mayor a cero");
        }
        if (dto.getCategoria() != null && dto.getCategoria().isBlank()) {
            errores.add("La categoría no puede estar vacía");
        }
        if (dto.getMarca() != null && dto.getMarca().isBlank()) {
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
