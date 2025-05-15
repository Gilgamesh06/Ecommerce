package com.ecommerce.venta;

import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.venta.model.dto.precio.PrecioAddDTO;
import com.ecommerce.venta.model.dto.producto.ProductoInfoDTO;
import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.entity.Producto;
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
import static org.mockito.Mockito.*;

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

    public static List<Producto> getProducts(){
        Producto producto1 = new Producto("camisa deportiva","CAM01LN","L","negro","camisa","hombre");
        Producto producto2 = new Producto("pantalon deportivo","PAN0136G","36","gris","pantalon","mujer");
        Producto producto3 = new Producto("pantaloneta casual","PAMCAS0134B","34","blanco","pantalon","hombre");
        return List.of(producto1,producto2,producto3);
    }

    @Test
    public void guardaProductosNoExitentesTest() {
        List<InventarioResponseDTO> inventario = getInventario();


        // Configura los mocks

        // Retorna un Optional.empty (un Optional vacio al ingresar cualquier parametro)
        when(this.productoRepository.findByReferenciaAndTallaAndColor(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        // Retorna el Objeto que se ingreso como parametro en el metodo save
        when(this.productoRepository.save(any(Producto.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Convierte los parametros precioUnid, precioVenta y producto en un PrecioAddDTO
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

        // Retonar un objeto precio con los atributos parceados del dto PrecioAddDTO
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

        // Metodo que se esta probando
        List<Producto> productosVenta = this.productoService.retornarProductosVenta(inventario);

        // Asegura de que productos no sea null y no este vacío
        assertNotNull(productosVenta);
        assertFalse(productosVenta.isEmpty());

        // Obtiene la lista de ProductoInfoDTO del la lista InventarioResponseDTO
        List<ProductoInfoDTO> productos = inventario.stream()
                .map(InventarioResponseDTO::getProductoInfoDTO)
                .toList();

        for (int i = 0; i< productosVenta.size(); i++ ){
            // Realiza las aserciones
            assertEquals(productos.get(i).getNombre(), productosVenta.get(i).getNombre());
            assertEquals(productos.get(i).getReferencia(), productosVenta.get(i).getReferencia());
            assertEquals(productos.get(i).getTalla(), productosVenta.get(i).getTalla());
            assertEquals(productos.get(i).getColor(), productosVenta.get(i).getColor());
        }
    }

    @Test
    public void retornarProductosExistentesTest(){
        List<InventarioResponseDTO> inventario = getInventario();
        List<Producto> productos = getProducts();

        // Retorna Producto 1
        when(this.productoRepository.findByReferenciaAndTallaAndColor(eq("CAM01LN"), eq("L"), eq("negro")))
                .thenReturn(Optional.of(productos.get(0)));

        // Retorna Producto 2
        when(this.productoRepository.findByReferenciaAndTallaAndColor(eq("PAN0136G"), eq("36"), eq("gris")))
                .thenReturn(Optional.of(productos.get(1)));

        // Retorna Producto 3
        when(this.productoRepository.findByReferenciaAndTallaAndColor(eq("PAMCAS0134B"), eq("34"), eq("blanco")))
                .thenReturn(Optional.of(productos.get(2)));

        // Convierte los parametros precioUnid, precioVenta y producto en un PrecioAddDTO
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

        // Retonar un objeto precio con los atributos parceados del dto PrecioAddDTO
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

        // Metodo que se esta probando
        List<Producto> productosVenta = this.productoService.retornarProductosVenta(inventario);

        // Asegura de que productos no sea null y no este vacío
        assertNotNull(productosVenta);
        assertFalse(productosVenta.isEmpty());

        for (int i = 0; i< productosVenta.size(); i++ ){
            // Realiza las aserciones
            assertEquals(productos.get(i).getNombre(), productosVenta.get(i).getNombre());
            assertEquals(productos.get(i).getReferencia(), productosVenta.get(i).getReferencia());
            assertEquals(productos.get(i).getTalla(), productosVenta.get(i).getTalla());
            assertEquals(productos.get(i).getColor(), productosVenta.get(i).getColor());
        }
    }

    @Test
    public void guardaProductosNoExistentesYRetornaProductosExistentesTest(){
        List<InventarioResponseDTO> inventario = getInventario();
        List<Producto> productos = getProducts();

        // Retorna Producto 1
        lenient().when(this.productoRepository.findByReferenciaAndTallaAndColor(eq("CAM01LN"), eq("L"), eq("negro")))
                .thenReturn(Optional.of(productos.get(0)));

        // Retorna Producto 2
        lenient().when(this.productoRepository.findByReferenciaAndTallaAndColor(eq("PAN0136G"), eq("36"), eq("gris")))
                .thenReturn(Optional.of(productos.get(1)));

        // Retorna Producto 3
        lenient().when(this.productoRepository.findByReferenciaAndTallaAndColor(anyString(),anyString(),anyString()))
                .thenReturn(Optional.empty());

        // Retorna el Objeto que se ingreso como parametro en el metodo save
        when(this.productoRepository.save(any(Producto.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Convierte los parametros precioUnid, precioVenta y producto en un PrecioAddDTO
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

        // Retonar un objeto precio con los atributos parceados del dto PrecioAddDTO
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

        // Metodo que se esta probando
        List<Producto> productosVenta = this.productoService.retornarProductosVenta(inventario);

        // Asegura de que productos no sea null y no este vacío
        assertNotNull(productosVenta);
        assertFalse(productosVenta.isEmpty());

        for (int i = 0; i< productosVenta.size(); i++ ){
            // Realiza las aserciones
            assertEquals(productos.get(i).getNombre(), productosVenta.get(i).getNombre());
            assertEquals(productos.get(i).getReferencia(), productosVenta.get(i).getReferencia());
            assertEquals(productos.get(i).getTalla(), productosVenta.get(i).getTalla());
            assertEquals(productos.get(i).getColor(), productosVenta.get(i).getColor());
        }
    }
}
