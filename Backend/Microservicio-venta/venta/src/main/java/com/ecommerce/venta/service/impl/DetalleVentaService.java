package com.ecommerce.venta.service.impl;

import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaAddDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaResposeDTO;
import com.ecommerce.venta.model.dto.producto.ProductoAddDTO;
import com.ecommerce.venta.model.dto.producto.ProductoResponseDTO;
import com.ecommerce.venta.model.entity.DetalleVenta;
import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.model.entity.Venta;
import com.ecommerce.venta.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final PrecioService precioService;
    private final ProductoService productoService;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository, PrecioService precioService,
                               ProductoService productoService){
        this.detalleVentaRepository = detalleVentaRepository;
        this.precioService = precioService;
        this.productoService = productoService;
    }

    public DetalleVentaResposeDTO converDetalleVentaResponseDTO(DetalleVenta detalleVenta){
        ProductoResponseDTO producto = this.productoService.convertProductoResposeDTO(detalleVenta.getProducto());
        DetalleVentaResposeDTO detalleVentaResposeDTO = new DetalleVentaResposeDTO();
        detalleVentaResposeDTO.setProducto(producto);
        detalleVentaResposeDTO.setCantidad(detalleVenta.getCantidad());
        detalleVentaResposeDTO.setPrecioTotal(detalleVenta.getPrecioTotal());
        return detalleVentaResposeDTO;
    }

    public DetalleVentaAddDTO convertDetalleVentaDTO(Venta venta,Producto producto, Integer cantidad ){
        return  new DetalleVentaAddDTO(venta,
                producto,
                cantidad);
    }

    public DetalleVenta agregarventa(DetalleVentaAddDTO detalleVentaAddDTO, double precioVenta){
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setVenta(detalleVentaAddDTO.getVenta());
        detalleVenta.setProducto(detalleVentaAddDTO.getProducto());
        detalleVenta.setCantidad(detalleVentaAddDTO.getCantidad());
        double valor = detalleVentaAddDTO.getCantidad()* precioVenta;
        detalleVenta.setPrecioTotal(valor);
        return detalleVenta;
    }


        public List<DetalleVentaResposeDTO> addDetalleVenta(List<DetalleVentaAddDTO> detalleVentaAddDTOs){
        List<DetalleVentaResposeDTO> detalleVentaResposeDTOS = new ArrayList<>();
        for(DetalleVentaAddDTO detalleVentaAddDTO: detalleVentaAddDTOs){
            Optional<Precio> precioOpt =this.precioService.getPrecio(detalleVentaAddDTO.getProducto().getId());
            if(precioOpt.isPresent()){
                DetalleVenta detalleVenta = agregarventa(detalleVentaAddDTO, precioOpt.get().getPrecioVenta());
                this.detalleVentaRepository.save(detalleVenta);
                detalleVentaResposeDTOS.add(converDetalleVentaResponseDTO(detalleVenta));
            }
        }
        return detalleVentaResposeDTOS;
    }
}
