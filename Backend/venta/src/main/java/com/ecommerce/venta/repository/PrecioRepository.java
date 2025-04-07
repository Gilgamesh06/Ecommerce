package com.ecommerce.venta.repository;

import com.ecommerce.venta.model.entity.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {
    @Query("SELECT p FROM Precio p WHERE p.producto.id = :productoId AND p.fecha = (SELECT MAX(p2.fecha) FROM Precio p2 WHERE p2.producto.id = :productoId)")
    Optional<Precio> findLatestPrecioByProductoId(@Param("productoId") Long productoId);
}

