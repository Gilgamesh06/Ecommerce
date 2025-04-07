package com.ecommerce.venta.service.impl;

import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.venta.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository){
        this.ventaRepository = ventaRepository;
    }

    public void addElement(List<CarritoResponseDTO> carritoResponseDTOs){

    }
}
