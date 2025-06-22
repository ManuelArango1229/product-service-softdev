package com.softdev.product_service.use_cases;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarProductoInteractorTest {

    private ProductoRepositoryPort productoRepository;
    private BuscarProductoInteractor interactor;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepositoryPort.class);
        interactor = new BuscarProductoInteractor(productoRepository);
    }

    @Test
    @DisplayName("Debe retornar productos que coincidan con los filtros")
    void testBuscarConFiltros() {
        Producto producto = new Producto(1L, "Camiseta", 25.0, "Ropa", "Nike", 10);
        when(productoRepository.buscarPorFiltros("Camiseta", "Ropa", "Nike"))
                .thenReturn(List.of(producto));

        List<Producto> resultado = interactor.buscar("Camiseta", "Ropa", "Nike");

        assertEquals(1, resultado.size());
        assertEquals("Camiseta", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe retornar true si el producto existe por nombre")
    void testExisteProducto() {
        when(productoRepository.findByNombre("Camiseta"))
                .thenReturn(Optional.of(new Producto(1L, "Camiseta", 25.0, "Ropa", "Nike", 5)));

        assertTrue(interactor.existe("Camiseta"));
    }

    @Test
    @DisplayName("Debe retornar false si el producto no existe")
    void testNoExisteProducto() {
        when(productoRepository.findByNombre("Pantalón"))
                .thenReturn(Optional.empty());

        assertFalse(interactor.existe("Pantalón"));
    }

    @Test
    @DisplayName("Debe retornar producto con stock si existe y tiene stock")
    void testBuscarStockDisponible() {
        Producto producto = new Producto(1L, "Zapatos", 50.0, "Calzado", "Adidas", 8);
        when(productoRepository.findByNombre("Zapatos")).thenReturn(Optional.of(producto));

        List<Producto> resultado = interactor.buscarStock("Zapatos");

        assertEquals(1, resultado.size());
        assertEquals("Zapatos", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe retornar lista vacía si el producto no tiene stock")
    void testBuscarSinStock() {
        Producto producto = new Producto(2L, "Zapatos", 50.0, "Calzado", "Adidas", 0);
        when(productoRepository.findByNombre("Zapatos")).thenReturn(Optional.of(producto));

        List<Producto> resultado = interactor.buscarStock("Zapatos");

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe retornar lista vacía si el producto no existe")
    void testBuscarProductoNoExisteStock() {
        when(productoRepository.findByNombre("Desconocido")).thenReturn(Optional.empty());

        List<Producto> resultado = interactor.buscarStock("Desconocido");

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe retornar el precio del producto si existe")
    void testGetPrecioProducto() {
        when(productoRepository.findByNombre("Gorra"))
                .thenReturn(Optional.of(new Producto(3L, "Gorra", 15.0, "Accesorios", "Puma", 4)));

        Double precio = interactor.getPrecio("Gorra");

        assertNotNull(precio);
        assertEquals(15.0, precio);
    }

    @Test
    @DisplayName("Debe retornar null si el producto no existe al obtener precio")
    void testGetPrecioProductoNoExiste() {
        when(productoRepository.findByNombre("Sombrero")).thenReturn(Optional.empty());

        Double precio = interactor.getPrecio("Sombrero");

        assertNull(precio);
    }
}
