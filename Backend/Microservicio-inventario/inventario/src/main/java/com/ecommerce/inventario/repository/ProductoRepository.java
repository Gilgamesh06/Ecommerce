package com.ecommerce.inventario.repository;

import com.ecommerce.inventario.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    Optional<Producto> findByReferenciaAndTallaAndColor(String referencia, String talla, String color);
}
