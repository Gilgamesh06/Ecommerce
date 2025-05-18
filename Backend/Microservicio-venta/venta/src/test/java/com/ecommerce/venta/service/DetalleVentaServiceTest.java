package com.ecommerce.venta.service;

import com.ecommerce.venta.TestDataProvider;
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

    @InjectMocks
    private TestDataProvider testDataProvider;



    @Test
    public void agregarDetalleVentaTest(){

        List<Precio> precios = this.testDataProvider.getPrices();
        List<DetalleVentaAddDTO> detalleVentaAddDTOS = this.testDataProvider.getDetalleVentaAdd();
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
