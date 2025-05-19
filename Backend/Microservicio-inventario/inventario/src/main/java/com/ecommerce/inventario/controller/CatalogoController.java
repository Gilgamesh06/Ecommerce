package com.ecommerce.inventario.controller;

import com.ecommerce.inventario.model.dto.catalogo.ProductoAgrupadoDTO;
import com.ecommerce.inventario.service.impl.CatalogoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/catalogo")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService){
        this.catalogoService = catalogoService;
    }

    @GetMapping("/filtros")
    public ResponseEntity<Map<String, Set<String>>> getFiltros(){
        Map<String,Set<String>> filtros = this.catalogoService.TypesOfTheFilter();
        return new ResponseEntity<>(filtros,HttpStatus.OK);
    }


    @GetMapping("/{target}")
    public ResponseEntity<Page<ProductoAgrupadoDTO>> getCatalogo(
            @PathVariable String target,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String subtipo,
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<ProductoAgrupadoDTO> productos = this.catalogoService.filterProductsTargets(target, tipo, subtipo, pageable);

        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }



}
