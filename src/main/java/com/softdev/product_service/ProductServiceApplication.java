package com.softdev.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {
    /**
     * Método principal que inicia la API Gateway.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(final String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
