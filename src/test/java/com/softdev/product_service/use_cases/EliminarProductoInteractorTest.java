package com.softdev.product_service.use_cases;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.exceptions.ProductoNoEncontradoException;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EliminarProductoInteractorTest {

    private ProductoRepositoryPort productoRepository;
    private EliminarProductoInteractor interactor;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepositoryPort.class);
        interactor = new EliminarProductoInteractor(productoRepository);
    }

    @Test
    @DisplayName("Debe eliminar el producto si existe")
    void testEliminarProductoExistente() {
        Long productoId = 1L;
        Producto producto = new Producto(productoId, "Producto X", 10.0, "Categoria", "Marca", 5);

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));

        assertDoesNotThrow(() -> interactor.execute(productoId));
        verify(productoRepository).deleteById(productoId);
    }

    @Test
    @DisplayName("Debe lanzar excepción si el producto no existe")
    void testEliminarProductoNoExistente() {
        Long productoId = 2L;
        when(productoRepository.findById(productoId)).thenReturn(Optional.empty());

        ProductoNoEncontradoException exception = assertThrows(
                ProductoNoEncontradoException.class,
                () -> interactor.execute(productoId),
                "Se esperaba ProductoNoEncontradoException"
        );

        assertTrue(exception.getMessage().contains("Producto no encontrado"));
        verify(productoRepository, never()).deleteById(any());
    }
}
