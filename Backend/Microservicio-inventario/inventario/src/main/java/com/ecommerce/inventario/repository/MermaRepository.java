package com.ecommerce.inventario.repository;

import com.ecommerce.inventario.model.entity.Merma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MermaRepository extends JpaRepository<Merma,Long> {
}
