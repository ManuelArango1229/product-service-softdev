package com.softdev.product_service.infrastructure.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.exceptions.DatosInvalidosException;
import com.softdev.product_service.domain.exceptions.ProductoNoEncontradoException;
import com.softdev.product_service.use_cases.CrearProductoInteractor;
import com.softdev.product_service.use_cases.EditarProductoInteractor;
import com.softdev.product_service.use_cases.EliminarProductoInteractor;
import com.softdev.product_service.use_cases.dto.CrearProductoDTO;
import com.softdev.product_service.use_cases.dto.EditarProductoDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST que maneja las operaciones relacionadas con los productos.
 * Este controlador se encarga de exponer los endpoints para la gestión de
 * productos
 * en la aplicación.
 */
@RequiredArgsConstructor
@RequestMapping("producto")
@RestController
public class ProductoRestController {
    /**
     * Interactor encargado de la lógica de negocio para crear productos.
     */
    private final CrearProductoInteractor crearProductoInteractor;

    /**
     * Interactor encargado de la lógica de negocio para editar productos.
     */
    private final EditarProductoInteractor editarProductoInteractor;

    /**
     * Interactor encargado de la lógica de negocio para eliminar productos.
     */
    private final EliminarProductoInteractor eliminarProductoInteractor;

    /**
     * Endpoint para registrar un nuevo producto en el sistema.
     *
     * @param productoRequest El producto a registrar con sus datos validados
     * @return ResponseEntity con el estado de la operación:
     *         - HttpStatus.CREATED y mensaje de éxito si se registra correctamente
     *         - HttpStatus.INTERNAL_SERVER_ERROR y mensaje de error en caso de
     *         fallo
     */
    @PostMapping("registrar")
    public ResponseEntity<?> registrarProducto(@Valid @RequestBody final Producto productoRequest) {
        CrearProductoDTO newProductoDTO = new CrearProductoDTO(
            productoRequest.getNombre(),
            productoRequest.getPrecio(),
            productoRequest.getCategoria(),
            productoRequest.getMarca(),
            productoRequest.getStock());

        crearProductoInteractor.execute(newProductoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto registrado exitosamente.");
    }
    /**
     * Endpoint para editar un producto existente en el sistema.
     *
     * @param request El objeto EditarProductoDTO que contiene los datos del
     *     * producto a editar
     * @param id El ID del producto a editar
     * @return ResponseEntity con el estado de la operación:
     *     *         - HttpStatus.OK y el producto editado si se edita correctamente
     *     *         - HttpStatus.BAD_REQUEST y mensaje de error en caso de datos
     *     * inválidos
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable final Long id, @RequestBody final EditarProductoDTO request) {
        try {
            request.setId(id);
            Producto productoActualizado = editarProductoInteractor.execute(request);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Producto actualizado exitosamente");
            respuesta.put("producto", productoActualizado);

            return ResponseEntity.ok(respuesta);

        } catch (ProductoNoEncontradoException | DatosInvalidosException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al editar el producto"));
        }
    }

    /**
     * Endpoint para eliminar un producto existente en el sistema.
     *
     * @param id El ID del producto a eliminar
     * @return ResponseEntity con el estado de la operación:
     *         - HttpStatus.OK y mensaje de éxito si se elimina correctamente
     *         - HttpStatus.NOT_FOUND y mensaje de error si el producto no se
     *         encuentra
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable final Long id) {
        try {
            eliminarProductoInteractor.execute(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado exitosamente"));
        } catch (ProductoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al eliminar el producto, verifique el ID"));
        }
    }

}
