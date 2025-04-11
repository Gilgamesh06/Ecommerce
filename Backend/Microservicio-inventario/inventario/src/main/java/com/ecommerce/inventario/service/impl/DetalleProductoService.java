package com.ecommerce.inventario.service.impl;

import com.ecommerce.inventario.model.dto.DetalleProductoAddDTO;
import com.ecommerce.inventario.model.entity.DetalleProducto;
import com.ecommerce.inventario.repository.DetalleProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class DetalleProductoService {
    
    private final DetalleProductoRepository detalleProductoRepository;
    
    public DetalleProductoService(DetalleProductoRepository detalleProductoRepository){
        this.detalleProductoRepository = detalleProductoRepository;
    }
    
    
    public DetalleProducto addDetailProduct(DetalleProductoAddDTO detalleProductoAddDTO){
        DetalleProducto detalleProducto = new DetalleProducto();
        detalleProducto.setComposicion(detalleProductoAddDTO.getComposicion());
        detalleProducto.setDescripcion(detalleProductoAddDTO.getDescripcion());
        detalleProducto.setPais(detalleProductoAddDTO.getPais());
        return this.detalleProductoRepository.save(detalleProducto);
    }
}
