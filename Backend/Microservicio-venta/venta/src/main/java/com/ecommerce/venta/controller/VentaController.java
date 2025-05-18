package com.ecommerce.venta.controller;

import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.venta.model.dto.venta.VentaResponseDTO;
import com.ecommerce.venta.service.impl.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        return ventaResponseOpt.map(ventaResponseDTO -> new ResponseEntity<>(ventaResponseDTO, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @GetMapping("/obtener-venta/{referencia}")
    public ResponseEntity<VentaResponseDTO> getVenta(@PathVariable String referencia){
        Optional<VentaResponseDTO> ventaOpt = this.ventaService.getVentaByReference(referencia);
        return ventaOpt.map(ventaResponseDTO -> new ResponseEntity<>(ventaResponseDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/listar-ventas")
    public ResponseEntity<List<VentaResponseDTO>> getAllVentas(){
        List<VentaResponseDTO> ventaResponseDTOS = this.ventaService.getAllVentas();
        if(ventaResponseDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventaResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/obtener-detalles/{referencia}")
    public ResponseEntity<List<DetalleVentaResponseDTO>> getDetallesVenta(@PathVariable String referencia){
        List<DetalleVentaResponseDTO> detalleVentaResposeDTOS = this.ventaService.obtenerDetalles(referencia);
        if(detalleVentaResposeDTOS.isEmpty()){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(detalleVentaResposeDTOS, HttpStatus.OK);
    }
}
