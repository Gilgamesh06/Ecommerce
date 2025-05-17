package com.ecommerce.venta;

import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaAddDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.venta.model.dto.pedido.PedidoAddDTO;
import com.ecommerce.venta.model.dto.producto.ProductoInfoDTO;
import com.ecommerce.venta.model.dto.producto.ProductoResponseDTO;
import com.ecommerce.venta.model.dto.venta.VentaResponseDTO;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.model.entity.Venta;
import com.ecommerce.venta.repository.VentaRepository;
import com.ecommerce.venta.service.component.ValidationProduct;
import com.ecommerce.venta.service.component.VentaProducer;
import com.ecommerce.venta.service.connect.InventarioClient;
import com.ecommerce.venta.service.impl.DetalleVentaService;
import com.ecommerce.venta.service.impl.ProductoService;
import com.ecommerce.venta.service.impl.VentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;
    @Mock
    private InventarioClient inventarioClient;
    @Mock
    private ValidationProduct validationProduct;
    @Mock
    private ProductoService productoService;
    @Mock
    private DetalleVentaService detalleVentaService;
    @Mock
    private VentaProducer ventaProducer;
    @InjectMocks
    private VentaService ventaService;

    public static List<CarritoResponseDTO>  getCarrito(){
        CarritoResponseDTO carrito1 = new CarritoResponseDTO(1L,3);
        CarritoResponseDTO carrito2 = new CarritoResponseDTO(2L,4);
        CarritoResponseDTO carrito3 = new CarritoResponseDTO(3L,5);
        return  List.of(carrito1,carrito2,carrito3);
    }

    public static List<InventarioResponseDTO> getInventario(){
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

    public static List<Producto> getProducts(){
        Producto producto1 = new Producto("camisa deportiva","CAM01LN","L","negro","camisa","hombre");
        Producto producto2 = new Producto("pantalon deportivo","PAN0136G","36","gris","pantalon","mujer");
        Producto producto3 = new Producto("pantaloneta casual","PAMCAS0134B","34","blanco","pantalon","hombre");
        return List.of(producto1,producto2,producto3);
    }

    public static List<ProductoResponseDTO> getProductoResponse(){
        ProductoResponseDTO producto1 = new ProductoResponseDTO("camisa deportiva","CAM01LN","L","negro",30000.0);
        ProductoResponseDTO producto2 = new ProductoResponseDTO("pantalon deportivo","PAN0136G","36","gris",35000.0);
        ProductoResponseDTO producto3 = new ProductoResponseDTO("pantaloneta casual","PAMCAS0134B","34","blanco", 40000.0);
        return List.of(producto1,producto2,producto3);
    }

    public static  List<DetalleVentaResponseDTO> getDetalleVenta(){
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

    @Test
    public void createVentaTest() {

        List<CarritoResponseDTO> carrito = getCarrito();
        List<Producto> productos = getProducts();
        List<InventarioResponseDTO> inventario = getInventario();
        List<DetalleVentaResponseDTO> detalles = getDetalleVenta();

        // Retorna la lista de inventario responde cuando recibe una lista
        when(this.inventarioClient.getProducts(anyList())).thenReturn(inventario);

        // Retorna true cuando recibe carrito y inventario
        when(this.validationProduct.validarProductos(inventario, carrito)).thenReturn(true);

        // Retorna la lista de productos
        when(this.productoService.retornarProductosVenta(inventario)).thenReturn(productos);

        // Retorna un DetalleVentaAddDTO
        when(this.detalleVentaService.convertDetalleVentaDTO(any(Venta.class), any(Producto.class), anyInt())).thenAnswer(
                inv -> {
                    Venta venta = inv.getArgument(0);
                    Producto producto = inv.getArgument(1);
                    Integer cantidad = inv.getArgument(2);
                    DetalleVentaAddDTO detalleVentaDTO = new DetalleVentaAddDTO();
                    detalleVentaDTO.setVenta(venta);
                    detalleVentaDTO.setProducto(producto);
                    detalleVentaDTO.setCantidad(cantidad);
                    return detalleVentaDTO;
                }
        );

        // Retorna la lista de detalleVentaREspondeDTO
        when(this.detalleVentaService.addDetalleVenta(anyList())).thenReturn(detalles);

        // Retorna el mismo objeto que recibe
        when(this.ventaRepository.save(any(Venta.class))).thenAnswer(
                inv -> inv.getArgument(0)
        );

        doNothing().when(this.ventaProducer).sendAddPedido(any(PedidoAddDTO.class));

        doNothing().when(this.ventaProducer).sendUpdateProduct(anyList());

        // Metodo a probar
        Optional<VentaResponseDTO> ventaOpt = this.ventaService.addElement(carrito);

        assertFalse(ventaOpt.isEmpty());
        VentaResponseDTO venta = ventaOpt.get();
        assertEquals("Exitosa", venta.getEstado());
        List<DetalleVentaResponseDTO> detalleVentas = venta.getDetalleVenta();
        for (int i = 0; i< detalleVentas.size(); i++ ){
            // Realiza las aserciones
            assertEquals(detalles.get(i).getProducto().getNombre(), detalleVentas.get(i).getProducto().getNombre());
            assertEquals(detalles.get(i).getProducto().getReferencia(), detalleVentas.get(i).getProducto().getReferencia());
            assertEquals(detalles.get(i).getProducto().getTalla(), detalleVentas.get(i).getProducto().getTalla());
            assertEquals(detalles.get(i).getProducto().getColor(), detalleVentas.get(i).getProducto().getColor());
        }

    }
}
