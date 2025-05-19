package com.ecommerce.inventario.service;

import com.ecommerce.inventario.model.dto.catalogo.ProductoAgrupadoDTO;
import com.ecommerce.inventario.model.dto.detalleproducto.DetalleProductoResponseDTO;
import com.ecommerce.inventario.model.entity.DetalleProducto;
import com.ecommerce.inventario.model.entity.Producto;
import com.ecommerce.inventario.repository.ProductoRepository;
import com.ecommerce.inventario.service.impl.CatalogoService;
import com.ecommerce.inventario.service.impl.DetalleProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private DetalleProductoService detalleProductoService;

    @InjectMocks
    private CatalogoService catalogoService;

    private Pageable pageable;
    private DetalleProducto detalleProductoMockInstance;
    private DetalleProductoResponseDTO detalleProductoResponseDTOMockInstance;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
        detalleProductoMockInstance = new DetalleProducto("Detalle de producto base","composicion","china");
        detalleProductoResponseDTOMockInstance = new DetalleProductoResponseDTO("Info adicional convertida","composicion","china");
    }

    private Producto crearProducto(Long id,String referencia, String nombre,String tipo, String talla, String color, String target, String subtipo, Double precioUnid, Double precioVenta) {
        Producto producto = new Producto(referencia,nombre,tipo,talla,color,target,subtipo, precioUnid, precioVenta, detalleProductoMockInstance);
        producto.setId(id);
        return producto;
    }

    @Test
    @DisplayName("Debería llamar a getProductsByTarget y transformar cuando solo se proporciona target")
    void filterProductsTargets_whenOnlyTargetProvided_thenCallsGetProductsByTargetAndTransforms() {
        // Arrange
        String target = "Hombre";
        List<String> expectedApiTargets = Arrays.asList("Unisex", target);

        Producto p1 = crearProducto(1L, "REF001", "Camisa Formal","ropa", "M", "Blanco", "hombre", "camisa",30.00,40.00);
        Producto p2 = crearProducto(2L, "REF001", "Camisa Formal","ropa", "L", "Blanco","hombre","camisa", 30.00,40.00);
        Producto p3 = crearProducto(3L, "REF002", "Pantalón Casual","ropa", "32", "Azul","hombre","pantalon", 50.00,40.00);
        List<Producto> productosList = Arrays.asList(p1, p2, p3);
        Page<Producto> mockProductosPage = new PageImpl<>(productosList, pageable, productosList.size());

        when(productoRepository.findByTargetIn(eq(expectedApiTargets), eq(pageable))).thenReturn(mockProductosPage);
        when(detalleProductoService.converDetallePorductoResponse(any(DetalleProducto.class)))
                .thenReturn(detalleProductoResponseDTOMockInstance);

        // Act
        Page<ProductoAgrupadoDTO> resultado = catalogoService.filterProductsTargets(target, null, null, pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size(), "Debería haber 2 productos agrupados (REF001, REF002)");
        verify(productoRepository).findByTargetIn(eq(expectedApiTargets), eq(pageable));
        verify(productoRepository, never()).findByTargetInAndTipo(any(), any(), any());
        verify(productoRepository, never()).findByTargetInAndTipoAndSubtipo(any(), any(), any(), any());
        verify(detalleProductoService, times(3)).converDetallePorductoResponse(any(DetalleProducto.class));

        ProductoAgrupadoDTO dtoRef001 = resultado.getContent().stream().filter(dto -> "REF001".equals(dto.getReferencia())).findFirst().orElseThrow();
        assertEquals("Camisa Formal", dtoRef001.getNombre());
        assertEquals(Set.of("M", "L"), dtoRef001.getTallas());
        assertEquals(Set.of("Blanco"), dtoRef001.getColores());
        assertEquals(2, dtoRef001.getVariantes().size());
    }

    @Test
    @DisplayName("Debería llamar a getProductsByTarget y transformar cuando tipo es blank")
    void filterProductsTargets_whenTipoIsBlank_thenCallsGetProductsByTarget() {
        // Arrange
        String target = "Hombre";
        String tipoBlank = "   ";
        List<String> expectedApiTargets = Arrays.asList("Unisex", target);
        Page<Producto> mockProductosPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(productoRepository.findByTargetIn(eq(expectedApiTargets), eq(pageable))).thenReturn(mockProductosPage);
        // No se espera llamada a detalleProductoService si la lista está vacía

        // Act
        catalogoService.filterProductsTargets(target, tipoBlank, null, pageable);

        // Assert
        verify(productoRepository).findByTargetIn(eq(expectedApiTargets), eq(pageable));
        verify(productoRepository, never()).findByTargetInAndTipo(any(), any(), any());
    }


    @Test
    @DisplayName("Debería llamar a getProductsByTargetAndTipo y transformar cuando se proporciona target y tipo (subtipo null)")
    void filterProductsTargets_whenTargetAndTipoProvided_subtipoNull_thenCallsGetProductsByTargetAndTipoAndTransforms() {
        // Arrange
        String target = "Mujer";
        String tipo = "Ropa";
        List<String> expectedApiTargets = Arrays.asList("Unisex", target);

        Producto p1 = crearProducto(10L, "REF003", "Vestido Verano","ropa", "S", "Amarillo", "mujer", "camisa",45.00,40.00);
        List<Producto> productosList = List.of(p1);
        Page<Producto> mockProductosPage = new PageImpl<>(productosList, pageable, productosList.size());

        when(productoRepository.findByTargetInAndTipo(eq(expectedApiTargets), eq(tipo), eq(pageable))).thenReturn(mockProductosPage);
        when(detalleProductoService.converDetallePorductoResponse(any(DetalleProducto.class)))
                .thenReturn(detalleProductoResponseDTOMockInstance);

        // Act
        Page<ProductoAgrupadoDTO> resultado = catalogoService.filterProductsTargets(target, tipo, null, pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(productoRepository, never()).findByTargetIn(any(), any());
        verify(productoRepository).findByTargetInAndTipo(eq(expectedApiTargets), eq(tipo), eq(pageable));
        verify(productoRepository, never()).findByTargetInAndTipoAndSubtipo(any(), any(), any(), any());
        verify(detalleProductoService, times(1)).converDetallePorductoResponse(any(DetalleProducto.class));
    }

    @Test
    @DisplayName("Debería llamar a getProductsByTargetAndTipo y transformar cuando subtipo es blank")
    void filterProductsTargets_whenSubtipoIsBlank_thenCallsGetProductsByTargetAndTipo() {
        // Arrange
        String target = "Mujer";
        String tipo = "Ropa";
        String subtipoBlank = "";
        List<String> expectedApiTargets = Arrays.asList("Unisex", target);
        Page<Producto> mockProductosPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(productoRepository.findByTargetInAndTipo(eq(expectedApiTargets), eq(tipo), eq(pageable))).thenReturn(mockProductosPage);

        // Act
        catalogoService.filterProductsTargets(target, tipo, subtipoBlank, pageable);

        // Assert
        verify(productoRepository).findByTargetInAndTipo(eq(expectedApiTargets), eq(tipo), eq(pageable));
        verify(productoRepository, never()).findByTargetInAndTipoAndSubtipo(any(), any(), any(), any());
    }

    @Test
    @DisplayName("Debería llamar a getProductsByTargetAndTipoAndSubtipo y transformar cuando todos los parámetros son proporcionados")
    void filterProductsTargets_whenAllParamsProvided_thenCallsGetProductsByTargetAndTipoAndSubtipoAndTransforms() {
        // Arrange
        String target = "Niño";
        String tipo = "Calzado";
        String subtipo = "Deportivo";
        List<String> expectedApiTargets = Arrays.asList("Unisex", target);

        Producto p1 = crearProducto(20L, "REF004", "Zapatillas Correr","zapatos", "30", "Verde", "hombre", "deportivo",30.00, 35.00);
        List<Producto> productosList = List.of(p1);
        Page<Producto> mockProductosPage = new PageImpl<>(productosList, pageable, productosList.size());

        when(productoRepository.findByTargetInAndTipoAndSubtipo(eq(expectedApiTargets), eq(tipo), eq(subtipo), eq(pageable)))
                .thenReturn(mockProductosPage);
        when(detalleProductoService.converDetallePorductoResponse(any(DetalleProducto.class)))
                .thenReturn(detalleProductoResponseDTOMockInstance);

        // Act
        Page<ProductoAgrupadoDTO> resultado = catalogoService.filterProductsTargets(target, tipo, subtipo, pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(productoRepository, never()).findByTargetIn(any(), any());
        verify(productoRepository, never()).findByTargetInAndTipo(any(), any(), any());
        verify(productoRepository).findByTargetInAndTipoAndSubtipo(eq(expectedApiTargets), eq(tipo), eq(subtipo), eq(pageable));
        verify(detalleProductoService, times(1)).converDetallePorductoResponse(any(DetalleProducto.class));
    }

    @Test
    @DisplayName("Debería devolver Page vacío de DTOs cuando el repositorio devuelve Page vacío")
    void filterProductsTargets_whenRepositoryReturnsEmptyPage_thenReturnsEmptyPageOfDTOs() {
        // Arrange
        String target = "Unisex";
        List<String> expectedApiTargets = Arrays.asList("Unisex", target);
        Page<Producto> mockEmptyProductosPage = Page.empty(pageable);

        when(productoRepository.findByTargetIn(eq(expectedApiTargets), eq(pageable))).thenReturn(mockEmptyProductosPage);

        // Act
        Page<ProductoAgrupadoDTO> resultado = catalogoService.filterProductsTargets(target, null, null, pageable);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.getContent().isEmpty());
        assertEquals(0, resultado.getTotalElements());
        verify(productoRepository).findByTargetIn(eq(expectedApiTargets), eq(pageable));
        verify(detalleProductoService, never()).converDetallePorductoResponse(any());
    }
}
