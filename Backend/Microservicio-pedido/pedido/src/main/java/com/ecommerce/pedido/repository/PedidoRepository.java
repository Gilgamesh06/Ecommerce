package com.ecommerce.pedido.repository;

import com.ecommerce.pedido.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {

    Optional<Pedido> findByReferenciaVenta(String referencia);
}
