package com.ecommerce.venta.service;

import com.ecommerce.venta.TestDataProvider;
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

import static org.junit.jupiter.api.Assertions.*;
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

    // Instancimiento de datos
    private TestDataProvider testDataProvider = new TestDataProvider();

    @Test
    public void createVentaTest() {

        List<CarritoResponseDTO> carrito = this.testDataProvider.getCarrito();
        List<Producto> productos = this.testDataProvider.getProducts();
        List<InventarioResponseDTO> inventario = this.testDataProvider.getInventario();
        List<DetalleVentaResponseDTO> detalles = this.testDataProvider.getDetalleVentaResponse();

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

        // Envia mensaje para crear el pedido
        doNothing().when(this.ventaProducer).sendAddPedido(any(PedidoAddDTO.class));

        // Envia el mensaje para actualizar la cantidad de productos en el inventario
        doNothing().when(this.ventaProducer).sendUpdateProduct(anyList());

        // Metodo a probar
        Optional<VentaResponseDTO> ventaOpt = this.ventaService.addElement(carrito);

        // Validaciones
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

    @Test
    public void createVentaConCantidadInsuficientesDeProductosTest(){


        List<CarritoResponseDTO> carrito = this.testDataProvider.getCarrito();
        List<InventarioResponseDTO> inventario = this.testDataProvider.getInventario();

        // Retorna la lista de inventario responde cuando recibe una lista
        when(this.inventarioClient.getProducts(anyList())).thenReturn(inventario);

        // Retorna false cuando el tamaño de la listas es diferente o la cantidad de un productos de carrito es mayor
        // que la de inventario
        when(this.validationProduct.validarProductos(inventario, carrito)).thenReturn(false);

        // Metodo a Probar
        Optional<VentaResponseDTO> ventaOpt = this.ventaService.addElement(carrito);

        // Validaciones
        assertTrue(ventaOpt.isEmpty());
    }
}
