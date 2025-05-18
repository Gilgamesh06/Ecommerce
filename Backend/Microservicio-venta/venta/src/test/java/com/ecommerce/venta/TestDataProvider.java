package com.ecommerce.venta;

import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaAddDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.venta.model.dto.precio.PrecioAddDTO;
import com.ecommerce.venta.model.dto.producto.ProductoInfoDTO;
import com.ecommerce.venta.model.dto.producto.ProductoResponseDTO;
import com.ecommerce.venta.model.dto.venta.VentaResponseDTO;
import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.model.entity.Venta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestDataProvider {


    public List<Producto> getProducts(){
        Producto producto1 = new Producto("camisa deportiva","CAM01LN","L","negro","camisa","hombre");
        Producto producto2 = new Producto("pantalon deportivo","PAN0136G","36","gris","pantalon","mujer");
        Producto producto3 = new Producto("pantaloneta casual","PAMCAS0134B","34","blanco","pantalon","hombre");
        producto1.setId(1L);
        producto2.setId(2L);
        producto3.setId(3L);
        return List.of(producto1,producto2,producto3);
    }

    public List<InventarioResponseDTO> getInventario(){
        ProductoInfoDTO producto1 = new ProductoInfoDTO("CAM01LN","camisa deportiva","L","negro","hombre","camisa",35000.0,50000.0);
        ProductoInfoDTO producto2 = new ProductoInfoDTO("PAN0136G","pantalon deportivo","36","gris","mujer","pantalon",45000.0,70000.0);
        ProductoInfoDTO producto3 = new ProductoInfoDTO("PAMCAS0134B","pantaloneta casual","34","blanco","hombre","pantalon",25000.0,45000.0);
        InventarioResponseDTO inventario1 =  new InventarioResponseDTO(20,producto1);
        InventarioResponseDTO inventario2 =  new InventarioResponseDTO(30,producto2);
        InventarioResponseDTO inventario3 =  new InventarioResponseDTO(40,producto3);

        List<InventarioResponseDTO> inventario = new ArrayList<>();
        inventario.add(inventario1);
        inventario.add(inventario2);
        inventario.add(inventario3);
        return inventario;
    }

    public List<Precio> getPrices(){
        List<Precio> precios = new ArrayList<>();
        Double precioUnid = 30000.0;
        Double precioVenta = 40000.0;
        List<Producto> productos = getProducts();
        for(Producto producto : productos){
            Precio precio = new Precio(precioUnid,precioVenta, LocalDateTime.now(),producto);
            precios.add(precio);
            precioUnid = precioUnid + 5000;
            precioUnid = precioUnid + 5000;
        }
        return precios;
    }

    public PrecioAddDTO getPricesDTO(){
        List<Producto> productos = getProducts();
        Double precioUnid = 35000.0;
        Double precioVenta = 50000.0;
        Producto producto = productos.getFirst();
        producto.setId(1L);
        return new PrecioAddDTO(precioUnid,precioVenta,producto);
    }

    public List<Integer> getCantidad(){
        return List.of(3,4,5);
    }

    public List<CarritoResponseDTO>  getCarrito(){
        CarritoResponseDTO carrito1 = new CarritoResponseDTO(1L,3);
        CarritoResponseDTO carrito2 = new CarritoResponseDTO(2L,4);
        CarritoResponseDTO carrito3 = new CarritoResponseDTO(3L,5);
        return  List.of(carrito1,carrito2,carrito3);
    }

    public List<ProductoResponseDTO> getProductoResponse(){
        ProductoResponseDTO producto1 = new ProductoResponseDTO("camisa deportiva","CAM01LN","L","negro",30000.0);
        ProductoResponseDTO producto2 = new ProductoResponseDTO("pantalon deportivo","PAN0136G","36","gris",35000.0);
        ProductoResponseDTO producto3 = new ProductoResponseDTO("pantaloneta casual","PAMCAS0134B","34","blanco", 40000.0);
        return List.of(producto1,producto2,producto3);
    }
    public Venta getVenta(){

        return new Venta("0001","222222","En proceso", LocalDateTime.now(),0.0);
    }

    public List<DetalleVentaAddDTO> getDetalleVentaAdd(){
        List<DetalleVentaAddDTO> detalleVentaAddDTOS = new ArrayList<>();
        List<Producto> productos = getProducts();
        List<Integer> cantidad = getCantidad();
        Venta venta = getVenta();

        for(int i = 0; i< productos.size(); i++){
            DetalleVentaAddDTO detalleVentaAddDTO = new DetalleVentaAddDTO(venta,productos.get(i),cantidad.get(i));
            detalleVentaAddDTOS.add(detalleVentaAddDTO);
        }
        return  detalleVentaAddDTOS;
    }


    public  List<DetalleVentaResponseDTO> getDetalleVentaResponse(){
        List<ProductoResponseDTO>  productos = getProductoResponse();
        List<CarritoResponseDTO> carrito = getCarrito();
        List<DetalleVentaResponseDTO> detalles = new ArrayList<>();
        for(int i = 0; i < productos.size();i++){
            DetalleVentaResponseDTO detalleVentaResponseDTO = new DetalleVentaResponseDTO();
            detalleVentaResponseDTO.setProducto(productos.get(i));
            detalleVentaResponseDTO.setCantidad(carrito.get(i).getCantidad());
            detalleVentaResponseDTO.setPrecioTotal(carrito.get(i).getCantidad()*productos.get(i).getPrecioVenta());
            detalles.add(detalleVentaResponseDTO);
        }
        return detalles;
    }

    public List<VentaResponseDTO> getAllVentaResponseDTO(){
        List<DetalleVentaResponseDTO> detalles = getDetalleVentaResponse();
        VentaResponseDTO ventaResponseDTO = new VentaResponseDTO("0001","22222","Exitosa",LocalDateTime.now(),0.0,detalles);
        Double valor = detalles.stream()
                .map(DetalleVentaResponseDTO::getPrecioTotal)
                .reduce(0.0,Double::sum);
        ventaResponseDTO.setValorVenta(valor);
        return List.of(ventaResponseDTO);

    }
}
