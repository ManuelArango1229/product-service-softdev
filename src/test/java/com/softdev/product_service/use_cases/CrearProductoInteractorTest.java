package com.softdev.product_service.use_cases;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.exceptions.DatosInvalidosException;
import com.softdev.product_service.domain.exceptions.ProductoExisteException;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;
import com.softdev.product_service.use_cases.dto.CrearProductoDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrearProductoInteractorTest {

    private ProductoRepositoryPort productoRepository;
    private CrearProductoInteractor crearProductoInteractor;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepositoryPort.class);
        crearProductoInteractor = new CrearProductoInteractor(productoRepository);
    }

    @Test
    @DisplayName("Debe crear un producto cuando los datos son válidos y no existe previamente")
    void testCrearProductoExitoso() {
        CrearProductoDTO dto = new CrearProductoDTO("Camiseta", 29.99, "Ropa", "Nike", 50);

        when(productoRepository.findByNombre("Camiseta")).thenReturn(Optional.empty());

        Producto productoGuardado = new Producto(
                1L,
                dto.getNombre(),
                dto.getPrecio(),
                dto.getCategoria(),
                dto.getMarca(),
                dto.getStock()
        );

        when(productoRepository.save(any())).thenReturn(productoGuardado);

        Producto resultado = crearProductoInteractor.execute(dto);

        assertNotNull(resultado);
        assertEquals("Camiseta", resultado.getNombre());
        assertEquals(29.99, resultado.getPrecio());
        verify(productoRepository).save(any());
    }

    @Test
    @DisplayName("Debe lanzar ProductoExisteException si el producto ya existe")
    void testProductoYaExiste() {
        CrearProductoDTO dto = new CrearProductoDTO("Camiseta", 29.99, "Ropa", "Nike", 50);

        when(productoRepository.findByNombre("Camiseta"))
                .thenReturn(Optional.of(new Producto(1L, "Camiseta", 29.99, "Ropa", "Nike", 50)));

        assertThrows(ProductoExisteException.class, () -> crearProductoInteractor.execute(dto));
    }

    @Test
    @DisplayName("Debe lanzar DatosInvalidosException si los campos son inválidos")
    void testDatosInvalidos() {
        CrearProductoDTO dto = new CrearProductoDTO("", -10.0, "", "", -1);

        DatosInvalidosException exception = assertThrows(DatosInvalidosException.class,
                () -> crearProductoInteractor.execute(dto));

        String mensaje = exception.getMessage();
        assertTrue(mensaje.contains("El nombre no puede estar vacío"));
        assertTrue(mensaje.contains("El precio debe ser mayor a cero"));
        assertTrue(mensaje.contains("La categoría no puede estar vacía"));
        assertTrue(mensaje.contains("La marca no puede estar vacía"));
        assertTrue(mensaje.contains("El stock no puede ser negativo"));
    }
}
