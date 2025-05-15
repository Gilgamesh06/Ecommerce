package com.ecommerce.venta;

import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaAddDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.venta.model.dto.producto.ProductoResponseDTO;
import com.ecommerce.venta.model.entity.DetalleVenta;
import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.model.entity.Venta;
import com.ecommerce.venta.repository.DetalleVentaRepository;
import com.ecommerce.venta.service.impl.DetalleVentaService;
import com.ecommerce.venta.service.impl.PrecioService;
import com.ecommerce.venta.service.impl.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetalleVentaServiceTest {

    @Mock
    private PrecioService precioService;

    @Mock
    private ProductoService productoService;

    @Mock
    private DetalleVentaRepository detalleVentaRepository;

    @InjectMocks
    private DetalleVentaService detalleVentaService;


    public static List<Producto> getProducts(){
        Producto producto1 = new Producto("camisa deportiva","CAM01LN","L","negro","camisa","hombre");
        Producto producto2 = new Producto("camisa casual","CAM02MG","M","gris","camisa","hombre");
        Producto producto3 = new Producto("camisa formal","CAM03SB","S","blanco","camisa","hombre");
        producto1.setId(1L);
        producto2.setId(2L);
        producto3.setId(3L);
        return List.of(producto1,producto2,producto3);
    }

    public static List<Precio> getPrecios(){
        List<Precio> precios = new ArrayList<>();
        Double precioUnid = 30000.0;
        Double precioVenta = 40000.0;
        List<Producto> productos = getProducts();
        for(Producto producto : productos){
            Precio precio = new Precio(precioUnid,precioVenta,LocalDateTime.now(),producto);
            precios.add(precio);
            precioUnid = precioUnid + 5000;
            precioUnid = precioUnid + 5000;
        }
        return precios;
    }

    public static List<Integer> getCantidad(){
        return List.of(3,4,5);
    }

    public static Venta getVenta(){

        return new Venta("0001","222222","En proceso", LocalDateTime.now(),0.0);
    }

    public static List<DetalleVentaAddDTO> getDetalleVenta(){
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


    @Test
    public void agregarDetalleVentaTest(){

        List<Precio> precios = getPrecios();
        List<DetalleVentaAddDTO> detalleVentaAddDTOS = getDetalleVenta();
        // Configura los mocks

        // Retorna el precio que tiene vinculado el producto con Id 1L
        when(this.precioService.getPrecio(eq(1L))).thenReturn(Optional.of(precios.get(0)));

        // Retorna el precio que tiene vinculado el producto con Id 2L
        when(this.precioService.getPrecio(eq(2L))).thenReturn(Optional.of(precios.get(1)));

        // Retorna el precio que tiene vinculado el producto con Id 3L
        when(this.precioService.getPrecio(eq(3L))).thenReturn(Optional.of(precios.get(2)));


        // Retorna el mismo Objeto DetalleVenta Ingresado
        when(this.detalleVentaRepository.save(any(DetalleVenta.class))).thenAnswer(
                inv -> inv.getArgument(0)
        );

        // Convierte el objeto Producto en un ProductoResponseDTO
        when(this.productoService.convertProductoResposeDTO(any(Producto.class))).thenAnswer(
                inv ->{
                    Producto producto = inv.getArgument(0);
                    Optional<Precio> precioOpt = this.precioService.getPrecio(producto.getId());
                    ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO();
                    productoResponseDTO.setNombre(producto.getNombre());
                    productoResponseDTO.setReferencia(producto.getReferencia());
                    productoResponseDTO.setTalla(producto.getTalla());
                    productoResponseDTO.setColor(producto.getColor());
                    precioOpt.ifPresent(precio -> productoResponseDTO.setPrecioVenta(precio.getPrecioVenta()));
                    return productoResponseDTO;
                }
        );

        // Metodo a probar
        List<DetalleVentaResponseDTO> detalleVentas = this.detalleVentaService.addDetalleVenta(detalleVentaAddDTOS);

        for (int i = 0; i< detalleVentas.size(); i++ ){
            // Realiza las aserciones
            assertEquals(detalleVentaAddDTOS.get(i).getProducto().getNombre(), detalleVentas.get(i).getProducto().getNombre());
            assertEquals(detalleVentaAddDTOS.get(i).getProducto().getReferencia(), detalleVentas.get(i).getProducto().getReferencia());
            assertEquals(detalleVentaAddDTOS.get(i).getProducto().getTalla(), detalleVentas.get(i).getProducto().getTalla());
            assertEquals(detalleVentaAddDTOS.get(i).getProducto().getColor(), detalleVentas.get(i).getProducto().getColor());
        }

    }
}
