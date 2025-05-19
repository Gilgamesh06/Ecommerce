package com.ecommerce.inventario.service;

import com.ecommerce.inventario.TestDataProvider;
import com.ecommerce.inventario.model.dto.detalleproducto.DetalleProductoAddDTO;
import com.ecommerce.inventario.model.dto.detalleproducto.DetalleProductoResponseDTO;
import com.ecommerce.inventario.model.entity.DetalleProducto;
import com.ecommerce.inventario.repository.DetalleProductoRepository;
import com.ecommerce.inventario.service.impl.DetalleProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetalleProductoServiceTest {

    @Mock
    private DetalleProductoRepository detalleProductoRepository;

    @InjectMocks
    private DetalleProductoService detalleProductoService;

    private final TestDataProvider testDataProvider = new TestDataProvider();

    @Test
    public void addDetalleProductoTest(){

        // Datos a probar
        DetalleProductoAddDTO detalleProductoAddDTO = this.testDataProvider.getDetalleProductoAddDTO();;

        // Simula el comportamiento del metodo save de detalleProductoRepository Retorna el mismo objeto
        // que le ingresan
        when(this.detalleProductoRepository.save(any(DetalleProducto.class))).thenAnswer(
                inv -> inv.getArgument(0)
        );

        // Metodo a probar
        DetalleProducto detalleProducto = this.detalleProductoService.addDetailProduct(detalleProductoAddDTO);

        // Validaciones
        assertNotNull(detalleProducto);
        assertEquals(detalleProductoAddDTO.getDescripcion(), detalleProducto.getDescripcion());
        assertEquals(detalleProductoAddDTO.getComposicion(), detalleProducto.getComposicion());
        assertEquals(detalleProductoAddDTO.getPais(), detalleProducto.getPais());
    }

    @Test
    public void convertDetallePorductoResponseTest(){

        // Datos a probar
        DetalleProducto detalleProducto = this.testDataProvider.getDetalleProducto();

        // Metodo a probar
        DetalleProductoResponseDTO detalleProductoResponseDTO = this.detalleProductoService.converDetallePorductoResponse(detalleProducto);

        // Validaciones
        assertNotNull(detalleProductoResponseDTO);
        assertEquals(detalleProducto.getDescripcion(), detalleProductoResponseDTO.getDescripcion());
        assertEquals(detalleProducto.getComposicion(), detalleProductoResponseDTO.getComposicion());
        assertEquals(detalleProducto.getPais(), detalleProductoResponseDTO.getPais());
    }
}
