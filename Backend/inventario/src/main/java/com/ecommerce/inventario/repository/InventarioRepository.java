package com.ecommerce.inventario.repository;

import com.ecommerce.inventario.model.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario,Long> {

    Optional<Inventario> findByProductoId(Long productoId);
}
