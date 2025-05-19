package com.ecommerce.inventario.service.impl;

import com.ecommerce.inventario.model.dto.detalleproducto.DetalleProductoAddDTO;
import com.ecommerce.inventario.model.dto.detalleproducto.DetalleProductoResponseDTO;
import com.ecommerce.inventario.model.entity.DetalleProducto;
import com.ecommerce.inventario.repository.DetalleProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class DetalleProductoService {
    
    private final DetalleProductoRepository detalleProductoRepository;
    
    public DetalleProductoService(DetalleProductoRepository detalleProductoRepository){
        this.detalleProductoRepository = detalleProductoRepository;
    }

    private DetalleProductoAddDTO converDetallePorductoAdd(DetalleProducto detalleProducto){
        DetalleProductoAddDTO detalleProductoAddDTO = new DetalleProductoAddDTO();
        detalleProductoAddDTO.setComposicion(detalleProducto.getComposicion());
        detalleProductoAddDTO.setDescripcion(detalleProducto.getDescripcion());
        detalleProductoAddDTO.setPais(detalleProducto.getPais());
        return detalleProductoAddDTO;
    }

    public DetalleProductoResponseDTO converDetallePorductoResponse(DetalleProducto detalleProducto){
        DetalleProductoResponseDTO detalleProductoResponseDTO = new DetalleProductoResponseDTO();
        detalleProductoResponseDTO.setComposicion(detalleProducto.getComposicion());
        detalleProductoResponseDTO.setDescripcion(detalleProducto.getDescripcion());
        detalleProductoResponseDTO.setPais(detalleProducto.getPais());
        return detalleProductoResponseDTO;
    }

    public DetalleProducto addDetailProduct(DetalleProductoAddDTO detalleProductoAddDTO){
        // Se crea un detalle producto  y con los datos del DTO
        DetalleProducto detalleProducto = new DetalleProducto();
        detalleProducto.setComposicion(detalleProductoAddDTO.getComposicion());
        detalleProducto.setDescripcion(detalleProductoAddDTO.getDescripcion());
        detalleProducto.setPais(detalleProductoAddDTO.getPais());
        return this.detalleProductoRepository.save(detalleProducto);
    }
}
