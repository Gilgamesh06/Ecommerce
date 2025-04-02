package com.ecommerce.inventario.repository;

import com.ecommerce.inventario.model.entity.DetalleProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleProductoRepository extends JpaRepository<DetalleProducto,Long> {
}
