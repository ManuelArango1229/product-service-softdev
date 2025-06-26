package com.softdev.product_service.use_cases.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase que representa una solicitud para actualizar el stock de un producto.
 * Esta clase contiene los campos necesarios para realizar la operación de
 * actualización de stock.
 */

@AllArgsConstructor
@Getter
public class ActualizarStockRequest {
    /**
     * Nombre del producto cuyo stock se desea actualizar.
     */
    private String nombre;
    /**
     * Cantidad de stock a agregar o restar al producto.
     */
    private int cantidad;

    /**
     * Constructor vacío para la clase ActualizarStockRequest.
     * Este constructor es útil para la deserialización de objetos.
     */
    public ActualizarStockRequest() {
    }

}
