package com.ecommerce.inventario.controller;

import com.ecommerce.inventario.model.dto.InventarioAddDTO;
import com.ecommerce.inventario.model.dto.InventarioResponseDTO;
import com.ecommerce.inventario.model.dto.ProductoSearchDTO;
import com.ecommerce.inventario.model.entity.Inventario;
import com.ecommerce.inventario.service.impl.InventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ap1/v1/inventario")
public class InventarioController {


    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService){ this.inventarioService = inventarioService;}

    @GetMapping("/listar-todos")
    public ResponseEntity<List<InventarioResponseDTO>> getAll(){
        List<InventarioResponseDTO> inventarios = this.inventarioService.findAll();
        return new ResponseEntity<>(inventarios, HttpStatus.OK);
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
}
