package com.ecommerce.inventario.service;

import com.ecommerce.inventario.TestDataProvider;
import com.ecommerce.inventario.model.dto.producto.ProductoAddDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoResponseDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoSearchDTO;
import com.ecommerce.inventario.model.entity.DetalleProducto;
import com.ecommerce.inventario.model.entity.Producto;
import com.ecommerce.inventario.repository.ProductoRepository;
import com.ecommerce.inventario.service.impl.DetalleProductoService;
import com.ecommerce.inventario.service.impl.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private DetalleProductoService detalleProductoService;

    @InjectMocks
    private ProductoService productoService;

    private final TestDataProvider testDataProvider = new TestDataProvider();

    @Test
    public void createdProductTest(){

        // Datos a probar
        DetalleProducto detalleProducto = this.testDataProvider.getDetalleProducto();
        ProductoAddDTO productoAddDTO = this.testDataProvider.getProductoAddDTO();

        // Simula el metodo addDetailProduct del servicio detalleProducto
        when(this.detalleProductoService.addDetailProduct(productoAddDTO.getDetalleProductoAddDTO())).thenReturn(detalleProducto);

        // Simula el metodo save de producto Repository Retorna el objeto que recibe como parametro
        when(this.productoRepository.save(any(Producto.class))).thenAnswer(
            inv -> inv.getArgument(0)
        );

        // Metodo a probar
        ProductoResponseDTO productoResponseDTO = this.productoService.addElement(productoAddDTO);

        // Validaciones
        assertNotNull(productoResponseDTO);
        assertEquals(productoAddDTO.getNombre(), productoResponseDTO.getNombre());
        assertEquals(productoAddDTO.getSubtipo(),productoResponseDTO.getSubtipo());
        assertEquals(productoAddDTO.getPrecioVenta(),productoResponseDTO.getPrecioVenta());

    }


    @Test
    public void findByAtributesProductTest(){

        // Datos a probar
        ProductoSearchDTO productoSearchDTO = this.testDataProvider.getProductSearchDTO();
        Producto producto = this.testDataProvider.getProducto();

        // Simula el metodo findByReferenciaAndTallaAndColor de productoRepository acepta cualquier string como parametro y retorna un Optional Producto
        when(this.productoRepository.findByReferenciaAndTallaAndColor(anyString(),anyString(),anyString())).thenReturn(Optional.of(producto));


        // Metodo a probar
        Optional<ProductoResponseDTO> productoResponseOpt = this.productoService.findByAtributes(productoSearchDTO);

        // Validaciones
        assertFalse(productoResponseOpt.isEmpty());
        // Obtiene el ProductoResponseDTO
        ProductoResponseDTO productoResponse = productoResponseOpt.get();
        assertEquals(producto.getNombre(), productoResponse.getNombre());
        assertEquals(producto.getSubtipo(),productoResponse.getSubtipo());
        assertEquals(producto.getPrecioVenta(),productoResponse.getPrecioVenta());
    }
}
