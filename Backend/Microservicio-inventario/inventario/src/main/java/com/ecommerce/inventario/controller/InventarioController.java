package com.ecommerce.inventario.controller;

import com.ecommerce.inventario.model.dto.inventario.InventarioAddDTO;
import com.ecommerce.inventario.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoSearchDTO;
import com.ecommerce.inventario.service.impl.InventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {


    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService){ this.inventarioService = inventarioService;}

    @GetMapping("/listar-todos")
    public ResponseEntity<List<InventarioResponseDTO>> getAll(){
        List<InventarioResponseDTO> inventario = this.inventarioService.findAll();
        if(inventario.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(inventario, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<InventarioResponseDTO> getProduct(@RequestBody ProductoSearchDTO productoSearchDTO){
        Optional<InventarioResponseDTO> inventarioOpt = this.inventarioService.findByAtributes(productoSearchDTO);
        return inventarioOpt.map(inventarioResponseDTO -> new ResponseEntity<>(inventarioResponseDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/agregar")
    public ResponseEntity<InventarioResponseDTO> addProduct(@RequestBody InventarioAddDTO inventarioAddDTO){
        InventarioResponseDTO inventarioResponseDTO = this.inventarioService.addElement(inventarioAddDTO);
        return new ResponseEntity<>(inventarioResponseDTO,HttpStatus.CREATED);
    }

    @PostMapping("/listar-productos")
    public ResponseEntity<List<InventarioResponseDTO>> getListProduct(@RequestBody List<Long> productIds){
        List<InventarioResponseDTO> inventarioResponseDTO = this.inventarioService.getProducts(productIds);
        return new ResponseEntity<>(inventarioResponseDTO,HttpStatus.OK);
    }
}
