package com.softdev.product_service.use_cases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softdev.product_service.domain.entities.Producto;
import com.softdev.product_service.domain.repositories.ProductoRepositoryPort;
/**
 * Caso de uso para buscar productos por nombre o categoría.
 */
@Service
public class BuscarProductoInteractor {

    /**
    * Puerto del repositorio de productos.
    */
    private final ProductoRepositoryPort productoRepositoryPort;

    /**
     * Constructor del caso de uso.
     *
     * @param repositoryPort puerto del repositorio de productos
     */
    public BuscarProductoInteractor(final ProductoRepositoryPort repositoryPort) {
        this.productoRepositoryPort = repositoryPort;
    }

    /**
     * Busca productos que coincidan con los filtros.
     *
     * @param nombre    término parcial de nombre (opcional)
     * @param categoria categoría del producto (opcional)
     * @param marca     marca del producto (opcional)
     * @return lista de productos encontrados
     */
    public List<Producto> buscar(final String nombre, final String categoria, final String marca) {
        System.out.println("---- BUSCAR PRODUCTOS ----");
        System.out.println("nombre: " + nombre + " (" + (nombre != null ? nombre.getClass().getName() : "null") + ")");
        System.out.println("categoria: " + categoria + " (" + (categoria != null ? categoria.getClass().getName() : "null") + ")");
        System.out.println("marca: " + marca + " (" + (marca != null ? marca.getClass().getName() : "null") + ")");
        return productoRepositoryPort.buscarPorFiltros(nombre, categoria, marca);
    }

    /**
     * Busca un producto por su nombre y verifica si existe.
     *
     * @param nombre nombre del producto a buscar
     * @return producto encontrado
     */
    public boolean existe(final String nombre) {
        return productoRepositoryPort.findByNombre(nombre).isPresent();
    }

    /**
     * Busca un producto por su nombre y verifica si tiene stock.
     *
     * @param nombre nombre del producto a buscar
     * @return lista de productos encontrados con stock
     *         (puede estar vacía si no hay stock o el producto no existe)
     */
    public List<Producto> buscarStock(final String nombre) {
        return productoRepositoryPort.findByNombre(nombre)
                .filter(p -> p.getStock() > 0)
                .map(List::of)
                .orElse(List.of());
    }

}
