package com.ecommerce.venta.repository;

import com.ecommerce.venta.model.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Long> {

    Optional<Venta> findByReferencia(String referencia);
}
