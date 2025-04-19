package com.ecommerce.inventario.repository;

import com.ecommerce.inventario.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    Optional<Producto> findByReferenciaAndTallaAndColor(String referencia, String talla, String color);
    Page<Producto> findByTargetIn(List<String> targets, Pageable pageable);
    Page<Producto> findByTargetInAndTipo(List<String> targets, String tipo, Pageable pageable);
    Page<Producto> findByTargetInAndTipoAndSubtipo(List<String> targets, String tipo, String subtipo, Pageable pageable);


    @Query("SELECT DISTINCT p.tipo FROM producto p")
    Set<String> findDistinctTipos();

    @Query("SELECT DISTINCT p.subtipo FROM producto p WHERE p.tipo = :tipo")
    Set<String> findDistinctSubtiposByTipo(@Param("tipo") String tipo);
}
