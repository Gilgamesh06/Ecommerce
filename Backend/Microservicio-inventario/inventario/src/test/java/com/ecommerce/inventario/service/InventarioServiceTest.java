package com.ecommerce.inventario.service;

import com.ecommerce.inventario.TestDataProvider;
import com.ecommerce.inventario.model.dto.inventario.InventarioAddDTO;
import com.ecommerce.inventario.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoAddDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoSearchDTO;
import com.ecommerce.inventario.model.entity.Inventario;
import com.ecommerce.inventario.model.entity.Producto;
import com.ecommerce.inventario.repository.InventarioRepository;
import com.ecommerce.inventario.service.impl.InventarioService;
import com.ecommerce.inventario.service.impl.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private InventarioService inventarioService;

    private final TestDataProvider testDataProvider = new TestDataProvider();

    @Test
    public void createInventarioIsNotExitsTest(){

        // Datos a probar
        InventarioAddDTO inventarioAddDTO = this.testDataProvider.getInventarioAddDTO();
        Producto producto = this.testDataProvider.getProducto();

        // Simula que no exite el producto con los parametros que contiene el DTO ProdductoSearchDTO
        when(this.productoService.searchProduct(any(ProductoSearchDTO.class))).thenReturn(Optional.empty());

        // Simula el metodo que agrega el producto retorna un objeto de tipo producto
        when(this.productoService.addProduct(any(ProductoAddDTO.class))).thenReturn(producto);

        // Retorna el mismo objeto que se le pasa como parametro
        when(this.inventarioRepository.save(any(Inventario.class))).thenAnswer(
            inv -> inv.getArgument(0)
        );

        // Metodo a probar
        InventarioResponseDTO inventarioResponseDTO = this.inventarioService.addElement(inventarioAddDTO);

        // Validaciones
        assertNotNull(inventarioResponseDTO);
        assertEquals(inventarioAddDTO.getCantidad(), inventarioResponseDTO.getCantidad());
        assertEquals(inventarioAddDTO.getProductoAddDTO().getReferencia(), inventarioResponseDTO.getProductoInfoDTO().getReferencia());
        assertEquals(inventarioAddDTO.getProductoAddDTO().getNombre(), inventarioResponseDTO.getProductoInfoDTO().getNombre());
        assertEquals(inventarioAddDTO.getProductoAddDTO().getPrecioVenta(), inventarioResponseDTO.getProductoInfoDTO().getPrecioVenta());
        assertEquals(inventarioAddDTO.getProductoAddDTO().getPrecioUnid(), inventarioResponseDTO.getProductoInfoDTO().getPrecioUnid());

    }

    @Test
    public void createInventarioIsExitsTest(){

        // Datos a probar
        InventarioAddDTO inventarioAddDTO = this.testDataProvider.getInventarioAddDTO();
        Producto producto = this.testDataProvider.getProducto();
        Inventario inventario = this.testDataProvider.getInventario();

        // Simula que no exite el producto con los parametros que contiene el DTO ProdductoSearchDTO
        when(this.productoService.searchProduct(any(ProductoSearchDTO.class))).thenReturn(Optional.of(producto));

        // Retorna el Inventario Asociado a Producto
        when(this.inventarioRepository.findByProductoId(anyLong())).thenReturn(Optional.of(inventario));

        // Metodo a probar
        InventarioResponseDTO inventarioResponseDTO = this.inventarioService.addElement(inventarioAddDTO);

        // Validaciones
        assertNotNull(inventarioResponseDTO);
        assertEquals(inventarioAddDTO.getCantidad(), inventarioResponseDTO.getCantidad());
        assertEquals(inventarioAddDTO.getProductoAddDTO().getReferencia(), inventarioResponseDTO.getProductoInfoDTO().getReferencia());
        assertEquals(inventarioAddDTO.getProductoAddDTO().getNombre(), inventarioResponseDTO.getProductoInfoDTO().getNombre());
        assertEquals(inventarioAddDTO.getProductoAddDTO().getPrecioVenta(), inventarioResponseDTO.getProductoInfoDTO().getPrecioVenta());
        assertEquals(inventarioAddDTO.getProductoAddDTO().getPrecioUnid(), inventarioResponseDTO.getProductoInfoDTO().getPrecioUnid());

    }
}
