package com.ecommerce.venta.controller;

import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.venta.model.dto.venta.VentaResponseDTO;
import com.ecommerce.venta.service.impl.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService){
        this.ventaService = ventaService;
    }

    @PostMapping("/realiza-venta")
    public ResponseEntity<VentaResponseDTO> makeSale(@RequestBody List<CarritoResponseDTO> carritoResponseDTOs){

        Optional<VentaResponseDTO> ventaResponseOpt = this.ventaService.addElement(carritoResponseDTOs);
        return ventaResponseOpt.map(ventaResponseDTO -> new ResponseEntity<>(ventaResponseDTO, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
