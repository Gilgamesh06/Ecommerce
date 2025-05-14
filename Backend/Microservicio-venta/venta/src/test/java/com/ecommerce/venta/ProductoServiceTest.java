package com.ecommerce.venta;

import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.venta.model.dto.precio.PrecioAddDTO;
import com.ecommerce.venta.model.dto.producto.ProductoInfoDTO;
import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.repository.PrecioRepository;
import com.ecommerce.venta.repository.ProductoRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private PrecioService precioService;

    @InjectMocks
    private ProductoService productoService;

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

    @Test
    public void retornarProductosVentaTest() {
        List<InventarioResponseDTO> inventario = getInventario();

        // Asegúrate de que inventario no sea null y tenga elementos
        assertNotNull(inventario);
        assertFalse(inventario.isEmpty());

        // Configura los mocks
        when(this.productoRepository.findByReferenciaAndTallaAndColor(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        when(this.productoRepository.save(any(Producto.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        when(this.precioService.convertPrecioAddDTO(anyDouble(), anyDouble(), any(Producto.class)))
                .thenAnswer(inv -> {
                    Double precioUnid = inv.getArgument(0);
                    Double precioVenta = inv.getArgument(1);
                    Producto producto = inv.getArgument(2);

                    // Crea y devuelve un PrecioAddDTO con los valores proporcionados
                    PrecioAddDTO precioAddDTO = new PrecioAddDTO();
                    precioAddDTO.setPrecioUnid(precioUnid);
                    precioAddDTO.setPrecioVenta(precioVenta);
                    precioAddDTO.setProducto(producto);
                    return precioAddDTO;
                });

        when(this.precioService.addPrecio(any(PrecioAddDTO.class))).thenAnswer(
            inv -> {
                PrecioAddDTO addPrecio = inv.getArgument(0);
                Precio nuevoPrecio = new Precio();
                nuevoPrecio.setPrecioUnid(addPrecio.getPrecioUnid());
                nuevoPrecio.setPrecioVenta(addPrecio.getPrecioVenta());
                nuevoPrecio.setFecha(LocalDateTime.now());
                nuevoPrecio.setProducto(addPrecio.getProducto());
                return nuevoPrecio;
            }
        );

        // Llama al método que estás probando
        List<Producto> productos = this.productoService.retornarProductosVenta(inventario);

        // Asegúrate de que productos no sea null y no esté vacío
        assertNotNull(productos);
        assertFalse(productos.isEmpty());

        // Accede al primer elemento de inventario
        InventarioResponseDTO primerInventario = inventario.get(0);
        ProductoInfoDTO producto = primerInventario.getProductoInfoDTO();

        // Realiza las aserciones
        assertEquals(producto.getNombre(), productos.get(0).getNombre());
        assertEquals(producto.getReferencia(), productos.get(0).getReferencia());
    }
}
