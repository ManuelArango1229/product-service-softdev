package com.softdev.product_service.infrastructure.database.postgres.adapters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.infrastructure.database.postgres.entities.ProductoEntity;
import com.softdev.product_service.infrastructure.database.postgres.mappers.ProductoEntityMapper;
import com.softdev.product_service.infrastructure.database.postgres.repositories.ProductosJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProductoRepositoryAdapterTest {

    private ProductosJpaRepository productosJpaRepository;
    private ProductoRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        productosJpaRepository = mock(ProductosJpaRepository.class);
        adapter = new ProductoRepositoryAdapter(productosJpaRepository);
    }

    @Test
    void testSave() {
        Producto producto = new Producto(1L, "Café", 10.0, "Bebida", "Juan Valdez", 100);
        ProductoEntity productoEntity = ProductoEntityMapper.toEntity(producto);

        when(productosJpaRepository.save(any())).thenReturn(productoEntity);

        Producto resultado = adapter.save(producto);

        assertEquals(producto.getNombre(), resultado.getNombre());
        verify(productosJpaRepository, times(1)).save(any());
    }

    @Test
    void testBuscarPorFiltros() {
        ProductoEntity entity = new ProductoEntity("Pan", 2.5, "Panadería", "Bimbo", 50);
        when(productosJpaRepository.buscarPorFiltros("%Pan%", "%Panadería%", "%Bimbo%"))
                .thenReturn(List.of(entity));

        List<Producto> productos = adapter.buscarPorFiltros("Pan", "Panadería", "Bimbo");

        assertFalse(productos.isEmpty());
        assertEquals("Pan", productos.get(0).getNombre());
    }

    @Test
    void testBuscarPorFiltrosConParametrosNulos() {
        when(productosJpaRepository.buscarPorFiltros(null, null, null)).thenReturn(List.of());

        List<Producto> productos = adapter.buscarPorFiltros(null, null, null);

        assertTrue(productos.isEmpty());
    }

    @Test
    void testFindById() {
        ProductoEntity entity = new ProductoEntity("Té", 1.5, "Bebida", "Lipton", 40);
        when(productosJpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Producto> resultado = adapter.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Té", resultado.get().getNombre());
    }

    @Test
    void testFindByIdNotFound() {
        // Simula un Optional.empty(), lo que causará un NoSuchElementException en el adapter
        when(productosJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            adapter.findById(1L).get(); // Forzamos el .get() como lo hace internamente
        });

        verify(productosJpaRepository).findById(1L);
    }

    @Test
    void testFindByNombre() {
        ProductoEntity entity = new ProductoEntity("Galletas", 5.0, "Snacks", "Oreo", 30);
        when(productosJpaRepository.findByNombre("Galletas")).thenReturn(entity);

        Optional<Producto> resultado = adapter.findByNombre("Galletas");

        assertTrue(resultado.isPresent());
        assertEquals("Galletas", resultado.get().getNombre());
    }

    @Test
    void testFindByNombreAndIdNot() {
        ProductoEntity entity = new ProductoEntity("Cereal", 4.0, "Desayuno", "Kellogg's", 60);
        when(productosJpaRepository.findByNombreAndIdNot("Cereal", 1L)).thenReturn(entity);

        Optional<Producto> resultado = adapter.findByNombreAndIdNot("Cereal", 1L);

        assertTrue(resultado.isPresent());
        assertEquals("Cereal", resultado.get().getNombre());
    }

    @Test
    void testDeleteById() {
        doNothing().when(productosJpaRepository).deleteById(1L);

        adapter.deleteById(1L);

        verify(productosJpaRepository, times(1)).deleteById(1L);
    }
}
