package com.ecommerce.pedido.repository;

import com.ecommerce.pedido.model.entity.DetalleDevolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleDevolucionRepository  extends JpaRepository<DetalleDevolucion, Long> {
}
