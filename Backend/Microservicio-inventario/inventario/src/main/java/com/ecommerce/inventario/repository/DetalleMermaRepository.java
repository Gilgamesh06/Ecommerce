package com.ecommerce.inventario.repository;

import com.ecommerce.inventario.model.entity.DetalleMerma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleMermaRepository extends JpaRepository<DetalleMerma,Long> {
}
