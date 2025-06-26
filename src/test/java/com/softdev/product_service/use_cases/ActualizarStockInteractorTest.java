package com.softdev.product_service.use_cases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActualizarStockInteractorTest {

    private ProductoRepositoryPort productoRepository;
    private ActualizarStockInteractor interactor;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepositoryPort.class);
        interactor = new ActualizarStockInteractor(productoRepository);
    }

    @Test
    void actualizarStockConCantidadValidaDebeReducirStock() {
        // Arrange
        Producto producto = new Producto(1L, "Café", 10.0, "Bebida", "MarcaX", 20);
        when(productoRepository.findByNombre("Café")).thenReturn(Optional.of(producto));

        // Act
        interactor.actualizarStock("Café", 5);

        // Assert
        assertEquals(15, producto.getStock());
        verify(productoRepository).save(producto);
    }

    @Test
    void actualizarStockDeProductoInexistenteDebeLanzarExcepcion() {
        // Arrange
        when(productoRepository.findByNombre("Desconocido")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            interactor.actualizarStock("Desconocido", 5);
        });
        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void actualizarStockConCantidadMayorQueDisponibleDebeLanzarExcepcion() {
        // Arrange
        Producto producto = new Producto(2L, "Té", 8.0, "Bebida", "MarcaY", 3);
        when(productoRepository.findByNombre("Té")).thenReturn(Optional.of(producto));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            interactor.actualizarStock("Té", 5);
        });
        assertEquals("Stock insuficiente", exception.getMessage());
    }
}
