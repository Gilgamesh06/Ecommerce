package com.ecommerce.pedido;

import com.ecommerce.pedido.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoAddDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseCompleteDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseSimpleDTO;
import com.ecommerce.pedido.model.dto.producto.ProductoResponseDTO;
import com.ecommerce.pedido.model.entity.Pedido;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestDataProvider {

    public PedidoAddDTO getPedidoAddDTO(){
        return new PedidoAddDTO("REF-1",650000.0);
    }

    public Pedido getPedido(){

        PedidoAddDTO pedidoAddDTO = getPedidoAddDTO();

        Pedido pedido = new Pedido();
        pedido.setReferenciaVenta(pedidoAddDTO.getReferencia());
        pedido.setFechaRealizacion(LocalDateTime.now());
        pedido.setEstado("EN PREPARACION");
        pedido.setValorTotal(pedidoAddDTO.getPrecioTotal());
        return pedido;

    }

    public List<PedidoResponseSimpleDTO> getAllPedidoSimpleDTO(){
        PedidoResponseSimpleDTO pedido1 = new PedidoResponseSimpleDTO("REF-1","EN PREPARACION",LocalDateTime.now(),null,650000.0);
        PedidoResponseSimpleDTO pedido2 = new PedidoResponseSimpleDTO("REF-2","EN PREPARACION",LocalDateTime.now(),null,250000.0);
        PedidoResponseSimpleDTO pedido3 = new PedidoResponseSimpleDTO("REF-3","EN PREPARACION",LocalDateTime.now(),null,150000.0);
        return List.of(pedido1,pedido2,pedido3);
    }

    public List<ProductoResponseDTO> getProductoResponse(){
        ProductoResponseDTO producto1 = new ProductoResponseDTO("camisa deportiva","CAM01LN","L","negro",30000.0);
        ProductoResponseDTO producto2 = new ProductoResponseDTO("pantalon deportivo","PAN0136G","36","gris",35000.0);
        ProductoResponseDTO producto3 = new ProductoResponseDTO("pantaloneta casual","PAMCAS0134B","34","blanco", 40000.0);
        return List.of(producto1,producto2,producto3);
    }

    public  List<DetalleVentaResponseDTO> getDetalleVentaResponse(){
        List<ProductoResponseDTO>  productos = getProductoResponse();
        List<DetalleVentaResponseDTO> detalles = new ArrayList<>();
        List<Integer> cantidad = List.of(3,4,5);
        for(int i = 0; i < productos.size();i++){
            DetalleVentaResponseDTO detalleVentaResponseDTO = new DetalleVentaResponseDTO();
            detalleVentaResponseDTO.setProducto(productos.get(i));
            detalleVentaResponseDTO.setCantidad(cantidad.get(i));
            detalleVentaResponseDTO.setPrecioTotal(cantidad.get(i)*productos.get(i).getPrecioVenta());
            detalles.add(detalleVentaResponseDTO);
        }
        return detalles;
    }

    public List<PedidoResponseCompleteDTO> getAllPedidCompleteResponse(){
        List<DetalleVentaResponseDTO> detalles = getDetalleVentaResponse();
        PedidoResponseCompleteDTO pedido1 = new PedidoResponseCompleteDTO("REF-1","EN PREPARACION",LocalDateTime.now(),null,650000.0,detalles);
        detalles.remove(2);
        PedidoResponseCompleteDTO pedido2 = new PedidoResponseCompleteDTO("REF-2","EN PREPARACION",LocalDateTime.now(),null,250000.0,detalles);
        detalles.remove(1);
        PedidoResponseCompleteDTO pedido3 = new PedidoResponseCompleteDTO("REF-3","EN PREPARACION",LocalDateTime.now(),null,150000.0,detalles);
        return List.of(pedido1,pedido2,pedido3);
    }

}
