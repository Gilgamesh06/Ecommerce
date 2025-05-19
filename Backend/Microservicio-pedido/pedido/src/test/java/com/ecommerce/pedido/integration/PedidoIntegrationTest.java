package com.ecommerce.pedido.integration;


import com.ecommerce.pedido.TestDataProvider;
import com.ecommerce.pedido.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoAddDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseSimpleDTO;
import com.ecommerce.pedido.service.connect.VentaClient;
import com.ecommerce.pedido.service.impl.PedidoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PedidoService pedidoService;

    @MockitoBean
    private VentaClient ventaClient;

    private TestDataProvider testDataProvider = new TestDataProvider();

    @Test
    @Order(1)
    public void createPedidoTest(){

        // Obtiene el pedido a probar
        PedidoAddDTO pedidoAddDTO = this.testDataProvider.getPedidoAddDTO();

         PedidoResponseSimpleDTO pedido = this.pedidoService.addPedido(pedidoAddDTO);

         assertNotNull(pedido);
         assertEquals("REF-1",pedido.getReferencia());
         assertEquals("EN PREPARACION", pedido.getEstado());
    }

    @Test
    @Order(2)
    public void createPedidoSecondTest(){

        // Obtiene el pedido a probar
        PedidoAddDTO pedidoAddDTO = new PedidoAddDTO("REF-2",25000.0);

        PedidoResponseSimpleDTO pedido = this.pedidoService.addPedido(pedidoAddDTO);

        assertNotNull(pedido);
        assertEquals("REF-2",pedido.getReferencia());
        assertEquals("EN PREPARACION", pedido.getEstado());
    }

    @Test
    @Order(3)
    public void listPedidoSimpleTest() throws  Exception{

        // ´
        List<PedidoResponseSimpleDTO> pedidos = this.testDataProvider.getAllPedidoSimpleDTO();

        mockMvc.perform(get("/api/v1/pedido/listar-pedidos"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].referencia").value(pedidos.get(0).getReferencia()))
                .andExpect(jsonPath("$[0].estado").value(pedidos.get(0).getEstado()))
                .andExpect(jsonPath("$[0].precioTotal").value(pedidos.get(0).getPrecioTotal()));
    }

    @Test
    @Order(4)
    public void getPedidoByReference() throws Exception{

        // Lista de detalles del pedido
        List<DetalleVentaResponseDTO> detalles = this.testDataProvider.getDetalleVentaResponse();

        // Referencia del pedido que se desea listar
        String referencia = "REF-1";

        // Simulacion del comportamiento del llamado al microservicio de Venta
        when(this.ventaClient.getDetallePedido(eq(referencia))).thenReturn(detalles);

        mockMvc.perform(get("/api/v1/pedido/listar/{referencia}", referencia))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.referencia").value(referencia))
                .andExpect(jsonPath("$.detallesPedido.[0].producto.nombre").value("camisa deportiva"))
                .andExpect(jsonPath("$.detallesPedido.[0].producto.referencia").value("CAM01LN"));
    }

    @Test
    @Order(5)
    public void getAllPedidoByReference() throws Exception{
        // Lista de listas de detallesPedido
        List<List<DetalleVentaResponseDTO>> detallesPedido = new ArrayList<>();
        // Lista de detalles del pedido
        List<DetalleVentaResponseDTO> detalles = this.testDataProvider.getDetalleVentaResponse();
        detallesPedido.add(detalles);
        List<DetalleVentaResponseDTO> detalles2 = List.of(detalles.get(0),detalles.get(1));
        detallesPedido.add(detalles2);

        // Simulacion del comportamiento del llamado al microservicio de Venta
        when(this.ventaClient.getAllDetallePedido(anyList())).thenReturn(detallesPedido);

        mockMvc.perform(get("/api/v1/pedido/listar-completo"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].referencia").value("REF-1"))
                .andExpect(jsonPath("$[1].referencia").value("REF-2"))
                .andExpect(jsonPath("$[0].detallesPedido.[0].producto.nombre").value("camisa deportiva"))
                .andExpect(jsonPath("$[0].detallesPedido.[0].producto.referencia").value("CAM01LN"));
    }
}
