package com.ecommerce.venta.service.impl;

import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaAddDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.venta.model.dto.producto.ProductoResponseDTO;
import com.ecommerce.venta.model.entity.DetalleVenta;
import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.model.entity.Venta;
import com.ecommerce.venta.repository.DetalleVentaRepository;
import jakarta.transaction.Transactional;
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

    private List<DetalleVenta> findDetalleVenta(Long id){
        return this.detalleVentaRepository.findAllByVentaId(id);
    }

    public List<DetalleVentaResponseDTO> getDetalleVenta(Long id){
        List<DetalleVenta> detalleVentas = findDetalleVenta(id);
        return detalleVentas.stream()
                .map(this::converDetalleVentaResponseDTO)
                .toList();
    }

    public DetalleVentaResponseDTO converDetalleVentaResponseDTO(DetalleVenta detalleVenta){
        ProductoResponseDTO producto = this.productoService.convertProductoResposeDTO(detalleVenta.getProducto());
        DetalleVentaResponseDTO detalleVentaResposeDTO = new DetalleVentaResponseDTO();
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

    public DetalleVenta agregarVenta(DetalleVentaAddDTO detalleVentaAddDTO, Double precioVenta){
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setVenta(detalleVentaAddDTO.getVenta());
        detalleVenta.setProducto(detalleVentaAddDTO.getProducto());
        detalleVenta.setCantidad(detalleVentaAddDTO.getCantidad());
        double valor = detalleVentaAddDTO.getCantidad()* precioVenta;
        detalleVenta.setPrecioTotal(valor);
        return detalleVenta;
    }


        @Transactional
        public List<DetalleVentaResponseDTO> addDetalleVenta(List<DetalleVentaAddDTO> detalleVentaAddDTOs){
        // Crea una Lista de DTOs DetalleVentaRespondeDTO
        List<DetalleVentaResponseDTO> detalleVentaResposeDTOS = new ArrayList<>();
        // Itera la lista detalleVentaDTOS ingregasada como parametro en el metodo
        for(DetalleVentaAddDTO detalleVentaAddDTO: detalleVentaAddDTOs){
            // llama al metodo getPrecio del atributo precioService el cual recive el id del objeto Producto y
            // Retorna un Optional<Precio> el cual es el Objeto Precio con la fecha mas reciente
            Optional<Precio> precioOpt =this.precioService.getPrecio(detalleVentaAddDTO.getProducto().getId());
            if(precioOpt.isPresent()){
                // Si esta presente el objeto Precio en el Optional llama al metodo agregarVenta parandole los
                // parametros detallVentaAddDTO y y el atributo precioVenta obtenido de precioOpt.get()
                DetalleVenta detalleVenta = agregarVenta(detalleVentaAddDTO, precioOpt.get().getPrecioVenta());
                // Guarda el detalleVenta en la db
                this.detalleVentaRepository.save(detalleVenta);
                // Agregar a la lista el DTO de DetalleVentaResponseDTO
                detalleVentaResposeDTOS.add(converDetalleVentaResponseDTO(detalleVenta));
            }
        }
        // Retorna List<DetalleVentaResponseDTO>
        return detalleVentaResposeDTOS;
    }
}
