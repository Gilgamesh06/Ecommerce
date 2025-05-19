package com.ecommerce.inventario;

import com.ecommerce.inventario.model.dto.detalleproducto.DetalleProductoAddDTO;
import com.ecommerce.inventario.model.dto.inventario.InventarioAddDTO;
import com.ecommerce.inventario.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoAddDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoInfoDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoSearchDTO;
import com.ecommerce.inventario.model.entity.DetalleProducto;
import com.ecommerce.inventario.model.entity.Inventario;
import com.ecommerce.inventario.model.entity.Producto;
import org.springframework.stereotype.Component;
import java.util.Locale;

@Component
public class TestDataProvider {

    public DetalleProducto getDetalleProducto(){
        return new DetalleProducto("Camisa de algodón de manga larga.","100% algodón","España");
    }
    public DetalleProductoAddDTO getDetalleProductoAddDTO(){
        return new DetalleProductoAddDTO("Camisa de algodón de manga larga.","100% algodón","España");
    }



    public Producto getProducto(){
        DetalleProducto detalleProducto = getDetalleProducto();
        Producto producto = new Producto("CAM01LN","camisa deportiva","ropa","L","negro","hombre","camisa",35000.0,50000.0,detalleProducto);
        producto.setId(1L);
        return producto;
    }

    public ProductoSearchDTO getProductSearchDTO(){
        return new ProductoSearchDTO("CAM01LN","L","negro");
    }

    public ProductoAddDTO getProductoAddDTO(){
        DetalleProductoAddDTO detalleProducto = getDetalleProductoAddDTO();
        return new ProductoAddDTO("CAM01LN","camisa deportiva","L","negro","ropa","camisa","hombre",35000.0,50000.0,detalleProducto);
    }
    public InventarioAddDTO getInventarioAddDTO(){
        ProductoAddDTO productoAddDTO = getProductoAddDTO();
        Integer cantidad = 3;
        return new InventarioAddDTO(cantidad,productoAddDTO);
    }

    public Inventario getInventario(){
        Producto producto = getProducto();
        Integer cantidad = 3;
        return new Inventario(producto,cantidad);
    }

    public ProductoInfoDTO getproductoInfoDTO(){
        return new ProductoInfoDTO("CAM01LN","camisa deportiva","L","negro","hombre","camisa",35000.0,50000.0);
    }

    public InventarioResponseDTO getInventarioResponseDTO(){
        ProductoInfoDTO productoInfoDTO = getproductoInfoDTO();
        Integer cantidad = 3;
        return  new InventarioResponseDTO(cantidad,productoInfoDTO);
    }

    public String getInventarioAddDTOJson() {
        InventarioAddDTO inventarioAddDTO = getInventarioAddDTO();
        ProductoAddDTO productoAddDTO = inventarioAddDTO.getProductoAddDTO();
        DetalleProductoAddDTO detalleProductoAddDTO = productoAddDTO.getDetalleProductoAddDTO();

        // Construir el JSON en el formato requerido
        String productoSearchSend = String.format(Locale.US, // Especificar el Locale
                "{\"cantidad\":%d,\"productoAddDTO\":{\"referencia\":\"%s\",\"nombre\":\"%s\",\"talla\":\"%s\",\"tipo\":\"%s\",\"subtipo\":\"%s\",\"target\":\"%s\",\"color\":\"%s\",\"precioUnid\":%.1f,\"precioVenta\":%.1f,\"detalleProducto\":{\"descripcion\":\"%s\",\"composicion\":\"%s\",\"pais\":\"%s\"}}}",
                inventarioAddDTO.getCantidad(), // Primero la cantidad
                productoAddDTO.getReferencia(),
                productoAddDTO.getNombre(),
                productoAddDTO.getTalla(),
                productoAddDTO.getTipo(),
                productoAddDTO.getSubtipo(),
                productoAddDTO.getTarget(),
                productoAddDTO.getColor(),
                productoAddDTO.getPrecioUnid(),
                productoAddDTO.getPrecioVenta(),
                detalleProductoAddDTO.getDescripcion(),
                detalleProductoAddDTO.getComposicion(),
                detalleProductoAddDTO.getPais()
        );

        return productoSearchSend;
    }


}
