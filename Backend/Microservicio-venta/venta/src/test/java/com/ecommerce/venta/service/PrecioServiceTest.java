package com.ecommerce.venta.service;

import com.ecommerce.venta.TestDataProvider;
import com.ecommerce.venta.model.dto.precio.PrecioAddDTO;
import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.repository.PrecioRepository;
import com.ecommerce.venta.service.impl.PrecioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrecioServiceTest {

    @Mock
    private PrecioRepository precioRepository;

    @InjectMocks
    private PrecioService precioService;

    // Instancimiento de datos
    private TestDataProvider testDataProvider = new TestDataProvider();

    @Test
    public void addNewPriceTest(){
        PrecioAddDTO precio = this.testDataProvider.getPricesDTO();
        when(precioRepository.findLatestPrecioByProductoId(1L)).thenReturn(Optional.empty());

        when(precioRepository.save(any(Precio.class))).thenAnswer(
                inv -> inv.<Precio>getArgument(0)
        );

        Precio newPrice = this.precioService.addPrecio(precio);

        assertNotNull(newPrice);
        assertEquals(precio.getPrecioUnid(),newPrice.getPrecioUnid());
        assertEquals(precio.getPrecioVenta(),newPrice.getPrecioVenta());
        assertEquals(precio.getProducto(),newPrice.getProducto());
    }

    @Test
    public void updatePriceTest(){
        Double precioAntiguo = 30000.0;
        PrecioAddDTO updatePrecio = this.testDataProvider.getPricesDTO();
        Precio precio = new Precio(updatePrecio.getPrecioUnid(),precioAntiguo,LocalDateTime.now(),updatePrecio.getProducto());
        when(precioRepository.findLatestPrecioByProductoId(1L)).thenReturn(Optional.of(precio));

        when(precioRepository.save(any(Precio.class))).thenAnswer(
                inv -> inv.<Precio>getArgument(0)
        );

        Precio newPrice = this.precioService.addPrecio(updatePrecio);

        assertNotNull(newPrice);
        assertEquals(updatePrecio.getPrecioUnid(),newPrice.getPrecioUnid());
        assertEquals(updatePrecio.getPrecioVenta(),newPrice.getPrecioVenta());
        assertEquals(1L,newPrice.getProducto().getId());
    }

    @Test
    public void returnPriceTest(){
        PrecioAddDTO updatePrecio = this.testDataProvider.getPricesDTO();
        Precio precio = new Precio(updatePrecio.getPrecioUnid(), updatePrecio.getPrecioVenta(), LocalDateTime.now(),updatePrecio.getProducto());
        when(precioRepository.findLatestPrecioByProductoId(1L)).thenReturn(Optional.of(precio));

        Precio newPrice = this.precioService.addPrecio(updatePrecio);

        assertNotNull(newPrice);
        assertEquals(updatePrecio.getPrecioUnid(),newPrice.getPrecioUnid());
        assertEquals(updatePrecio.getPrecioVenta(),newPrice.getPrecioVenta());
        assertEquals(1L,newPrice.getProducto().getId());
    }
}
