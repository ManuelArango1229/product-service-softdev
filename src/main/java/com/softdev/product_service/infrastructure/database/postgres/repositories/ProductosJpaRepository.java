package com.softdev.product_service.infrastructure.database.postgres.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softdev.product_service.infrastructure.database.postgres.entities.ProductoEntity;

public interface ProductosJpaRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByName(String nombre);
}
