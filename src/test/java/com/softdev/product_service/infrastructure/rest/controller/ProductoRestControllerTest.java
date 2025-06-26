package com.softdev.product_service.infrastructure.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.exceptions.DatosInvalidosException;
import com.softdev.product_service.domain.exceptions.ProductoNoEncontradoException;
import com.softdev.product_service.use_cases.*;
import com.softdev.product_service.use_cases.dto.ActualizarStockRequest;
import com.softdev.product_service.use_cases.dto.EditarProductoDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoRestController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva la seguridad para pruebas
class ProductoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrearProductoInteractor crearProductoInteractor;

    @MockBean
    private EditarProductoInteractor editarProductoInteractor;

    @MockBean
    private EliminarProductoInteractor eliminarProductoInteractor;

    @MockBean
    private BuscarProductoInteractor buscarProductoInteractor;

    @MockBean
    private ActualizarStockInteractor actualizarStockInteractor;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("✅ Debería retornar 201 al registrar un producto")
    void registrarProducto() throws Exception {
        Producto producto = new Producto(1L, "Café", 12.5, "Bebidas", "JuanValdez", 100);

        mockMvc.perform(post("/producto/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Producto registrado exitosamente."));
    }

    @Test
    @DisplayName("✅ Debería editar producto exitosamente")
    void editarProducto() throws Exception {
        EditarProductoDTO dto = new EditarProductoDTO(1L, "Pan", 1.0, "Panadería", "MarcaX", 10);
        Producto productoEditado = new Producto(1L, "Pan", 1.0, "Panadería", "MarcaX", 10);

        when(editarProductoInteractor.execute(any(EditarProductoDTO.class))).thenReturn(productoEditado);

        mockMvc.perform(put("/producto/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Producto actualizado exitosamente"))
                .andExpect(jsonPath("$.producto.nombre").value("Pan"));
    }

    @Test
    @DisplayName("✅ Debería eliminar producto por ID")
    void eliminarProducto() throws Exception {
        mockMvc.perform(delete("/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Producto eliminado exitosamente"));
    }

    @Test
    @DisplayName("✅ Debería buscar productos y devolver lista")
    void buscarProductos() throws Exception {
        Producto producto = new Producto(1L, "Café", 12.5, "Bebidas", "JuanValdez", 100);
        when(buscarProductoInteractor.buscar(any(), any(), any()))
                .thenReturn(List.of(producto));

        mockMvc.perform(get("/producto/buscar")
                        .param("nombre", "café"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Café"));
    }

    @Test
    @DisplayName("✅ Debería actualizar stock correctamente")
    void actualizarStock() throws Exception {
        ActualizarStockRequest request = new ActualizarStockRequest("Café", 5);

        mockMvc.perform(put("/producto/stock/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Stock actualizado correctamente"));
    }

    @Test
    @DisplayName("✅ Debería indicar que el producto existe")
    void existeProducto() throws Exception {
        when(buscarProductoInteractor.existe("Café")).thenReturn(true);

        mockMvc.perform(get("/producto/existe/Café"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.existe").value(true));
    }

    @Test
    @DisplayName("✅ Debería retornar stock del producto")
    void verificarStock() throws Exception {
        Producto producto = new Producto(1L, "Café", 12.5, "Bebidas", "Marca", 50);
        when(buscarProductoInteractor.buscarStock("Café")).thenReturn(List.of(producto));

        mockMvc.perform(get("/producto/stock/Café"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(50));
    }

    @Test
    @DisplayName("❌ Debería retornar error si no hay stock")
    void verificarStockProductoNoEncontrado() throws Exception {
        when(buscarProductoInteractor.buscarStock("Desconocido")).thenReturn(List.of());

        mockMvc.perform(get("/producto/stock/Desconocido"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Producto no encontrado o sin stock"));
    }

    @Test
    @DisplayName("✅ Debería retornar precio del producto")
    void verificarPrecio() throws Exception {
        when(buscarProductoInteractor.getPrecio("Café")).thenReturn(10.5);

        mockMvc.perform(get("/producto/precio/Café"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio").value(10.5));
    }

    @Test
    @DisplayName("❌ Debería retornar error si el producto no tiene precio")
    void verificarPrecioProductoNoEncontrado() throws Exception {
        when(buscarProductoInteractor.getPrecio("Desconocido")).thenReturn(null);

        mockMvc.perform(get("/producto/precio/Desconocido"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Producto no encontrado"));
    }

    @Test
    @DisplayName("✅ Debería retornar mensaje cuando no hay productos encontrados")
    void buscarProductosNoEncontrados() throws Exception {
        when(buscarProductoInteractor.buscar(any(), any(), any())).thenReturn(List.of());

        mockMvc.perform(get("/producto/buscar").param("nombre", "Nada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("No se encontraron productos con los criterios proporcionados."));
    }

    @Test
    @DisplayName("❌ Debería manejar error al actualizar stock")
    void actualizarStockConError() throws Exception {
        ActualizarStockRequest request = new ActualizarStockRequest("Café", 5);
        doThrow(new RuntimeException("Producto no encontrado")).when(actualizarStockInteractor)
                .actualizarStock("Café", 5);

        mockMvc.perform(put("/producto/stock/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Producto no encontrado"));
    }

    @Test
    @DisplayName("❌ Debería manejar error al eliminar producto")
    void eliminarProductoConError() throws Exception {
        doThrow(new RuntimeException("Error")).when(eliminarProductoInteractor).execute(99L);

        mockMvc.perform(delete("/producto/99"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Error al eliminar el producto, verifique el ID"));
    }

    @Test
    @DisplayName("❌ Debería manejar error al editar producto")
    void editarProductoConError() throws Exception {
        EditarProductoDTO dto = new EditarProductoDTO(1L, "Pan", 1.0, "Panadería", "MarcaX", 10);
        doThrow(new RuntimeException("Error inesperado")).when(editarProductoInteractor).execute(any());

        mockMvc.perform(put("/producto/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Error al editar el producto"));
    }

    @Test
    @DisplayName("❌ Debería manejar ProductoNoEncontradoException al editar")
    void editarProductoNoEncontrado() throws Exception {
        EditarProductoDTO dto = new EditarProductoDTO(1L, "Pan", 1.0, "Panadería", "MarcaX", 10);
        doThrow(new ProductoNoEncontradoException("Producto no encontrado")).when(editarProductoInteractor).execute(any());

        mockMvc.perform(put("/producto/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Producto no encontrado"));
    }

    @Test
    @DisplayName("❌ Debería manejar DatosInvalidosException al editar")
    void editarProductoDatosInvalidos() throws Exception {
        EditarProductoDTO dto = new EditarProductoDTO(1L, "Pan", 1.0, "Panadería", "MarcaX", 10);
        doThrow(new DatosInvalidosException("Datos inválidos")).when(editarProductoInteractor).execute(any());

        mockMvc.perform(put("/producto/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Datos inválidos"));
    }

    @Test
    @DisplayName("❌ Debería manejar ProductoNoEncontradoException al eliminar")
    void eliminarProductoNoEncontrado() throws Exception {
        doThrow(new ProductoNoEncontradoException("Producto no encontrado")).when(eliminarProductoInteractor).execute(100L);

        mockMvc.perform(delete("/producto/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Producto no encontrado"));
    }


}
