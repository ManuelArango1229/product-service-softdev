package com.softdev.product_service.use_cases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.exceptions.DatosInvalidosException;
import com.softdev.product_service.domain.exceptions.ProductoNoEncontradoException;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;
import com.softdev.product_service.use_cases.dto.EditarProductoDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EditarProductoInteractorTest {

    private ProductoRepositoryPort productoRepository;
    private EditarProductoInteractor interactor;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepositoryPort.class);
        interactor = new EditarProductoInteractor(productoRepository);
    }

    @Test
    void editarProductoConDatosValidosDebeActualizarCorrectamente() {
        // Arrange
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 5.0, "Panadería", "MarcaA", 10);

        EditarProductoDTO dto = new EditarProductoDTO(id, "Pan Integral", 6.0, "Panadería", "MarcaA", 15);

        when(productoRepository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.findByNombre("Pan Integral")).thenReturn(Optional.empty());
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Producto actualizado = interactor.execute(dto);

        // Assert
        assertEquals("Pan Integral", actualizado.getNombre());
        assertEquals(6.0, actualizado.getPrecio());
        assertEquals(15, actualizado.getStock());
    }

    @Test
    void editarProductoInexistenteDebeLanzarExcepcion() {
        // Arrange
        Long id = 999L;
        EditarProductoDTO dto = new EditarProductoDTO(id, "ProductoX", 10.0, "General", "Marca", 5);

        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductoNoEncontradoException.class, () -> interactor.execute(dto));
    }

    @Test
    void editarProductoConNombreExistenteDebeLanzarExcepcion() {
        // Arrange
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 5.0, "Panadería", "MarcaA", 10);
        EditarProductoDTO dto = new EditarProductoDTO(id, "YaExiste", null, null, null, null);

        when(productoRepository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.findByNombre("YaExiste")).thenReturn(Optional.of(new Producto(2L, "YaExiste", 10.0, "General", "MarcaB", 5)));
        // Act & Assert
        assertThrows(DatosInvalidosException.class, () -> interactor.execute(dto));
    }

    @Test
    void editarProductoConPrecioInvalidoDebeLanzarExcepcion() {
        // Arrange
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 5.0, "Panadería", "MarcaA", 10);
        EditarProductoDTO dto = new EditarProductoDTO(id, null, -5.0, null, null, null);

        when(productoRepository.findById(id)).thenReturn(Optional.of(productoExistente));

        // Act & Assert
        DatosInvalidosException exception = assertThrows(DatosInvalidosException.class, () -> interactor.execute(dto));
        assertTrue(exception.getMessage().contains("El precio debe ser mayor a cero"));
    }

    @Test
    void editarProductoSinCambiosDebeGuardarSinErrores() {
        // Arrange
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 5.0, "Panadería", "MarcaA", 10);
        EditarProductoDTO dto = new EditarProductoDTO(id, null, null, null, null, null);

        when(productoRepository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // Act
        Producto resultado = interactor.execute(dto);

        // Assert
        assertEquals(productoExistente, resultado);
    }
}
