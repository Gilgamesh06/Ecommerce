package com.ecommerce.venta.integration;

import com.ecommerce.venta.TestDataProvider;
import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.venta.model.dto.pedido.PedidoAddDTO;
import com.ecommerce.venta.service.component.VentaProducer;
import com.ecommerce.venta.service.connect.InventarioClient;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VentaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockitoBean
    private InventarioClient inventarioClient;

    @MockitoBean
    private VentaProducer ventaProducer;

    // Instancimiento de datos
    private TestDataProvider testDataProvider = new TestDataProvider();

    @Test
    @Order(1)
    public void createdVentaTresProductosTest() throws Exception{

        List<InventarioResponseDTO> inventario = this.testDataProvider.getInventario();

        // Retorna la lista de inventario responde cuando recibe una lista
        when(this.inventarioClient.getProducts(anyList())).thenReturn(inventario);

        // Envia mensaje para crear el pedido
        doNothing().when(this.ventaProducer).sendAddPedido(any(PedidoAddDTO.class));

        // Envia el mensaje para actualizar la cantidad de productos en el inventario
        doNothing().when(this.ventaProducer).sendUpdateProduct(anyList());

        // Datos que debe recibir el EndPoint
        String jsonRequest = String.format("[{\"productoId\":1,\"cantidad\":3},{\"productoId\":2,\"cantidad\":4},{\"productoId\":3,\"cantidad\":5}]");


        mockMvc.perform(post("/api/v1/ventas/realiza-venta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("referencia").value("REF-1"))
                .andExpect(jsonPath("estado").value("Exitosa"));
    }


    @Test
    @Order(2)
    public void createdVentaDosProductosTest() throws Exception{

        List<InventarioResponseDTO> inventario = this.testDataProvider.getInventario();
        inventario.remove(2);

        // Retorna la lista de inventario responde cuando recibe una lista
        when(this.inventarioClient.getProducts(anyList())).thenReturn(inventario);

        // Envia mensaje para crear el pedido
        doNothing().when(this.ventaProducer).sendAddPedido(any(PedidoAddDTO.class));

        // Envia el mensaje para actualizar la cantidad de productos en el inventario
        doNothing().when(this.ventaProducer).sendUpdateProduct(anyList());

        // Datos que debe recibir el EndPoint
        String jsonRequest = String.format("[{\"productoId\":1,\"cantidad\":3},{\"productoId\":2,\"cantidad\":4}]");


        mockMvc.perform(post("/api/v1/ventas/realiza-venta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("referencia").value("REF-2"))
                .andExpect(jsonPath("estado").value("Exitosa"));
    }

    @Test
    @Order(3)
    public void createdVentaUnProductoTest() throws Exception{

        List<InventarioResponseDTO> inventario = this.testDataProvider.getInventario();
        inventario.remove(2);
        inventario.remove(1);

        // Retorna la lista de inventario responde cuando recibe una lista
        when(this.inventarioClient.getProducts(anyList())).thenReturn(inventario);

        // Envia mensaje para crear el pedido
        doNothing().when(this.ventaProducer).sendAddPedido(any(PedidoAddDTO.class));

        // Envia el mensaje para actualizar la cantidad de productos en el inventario
        doNothing().when(this.ventaProducer).sendUpdateProduct(anyList());

        // Datos que debe recibir el EndPoint
        String jsonRequest = String.format("[{\"productoId\":1,\"cantidad\":3}]");


        mockMvc.perform(post("/api/v1/ventas/realiza-venta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("referencia").value("REF-3"))
                .andExpect(jsonPath("estado").value("Exitosa"));
    }

    @Test
    @Order(4)
    public void getAllVentaTest() throws Exception{

        mockMvc.perform(get("/api/v1/ventas/listar-ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].referencia").value("REF-1"))
                .andExpect(jsonPath("$[1].referencia").value("REF-2"))
                .andExpect(jsonPath("$[2].referencia").value("REF-3"))
                .andExpect(jsonPath("$[0].estado").value("Exitosa"))
                .andExpect(jsonPath("$[0].valorVenta").value(655000));
    }


    @Test
    @Order(4)
    public void getVentaByReferenceTest() throws Exception{

        String referencia = "REF-3";

        mockMvc.perform(get("/api/v1/ventas/obtener-venta/{referencia}", referencia))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.referencia").value("REF-3"))
                .andExpect(jsonPath("$.estado").value("Exitosa"))
                .andExpect(jsonPath("$.valorVenta").value(150000))
                .andExpect(jsonPath("$.detalleVenta[0].producto.nombre").value("camisa deportiva"))
                .andExpect(jsonPath("$.detalleVenta[0].producto.referencia").value("CAM01LN"));
    }

    @Test
    @Order(4)
    public void getDetalleVentaByReferenceTest() throws Exception{

        String referencia = "REF-1";

        mockMvc.perform(get("/api/v1/ventas/obtener-detalles/{referencia}", referencia))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidad").value(3))
                .andExpect(jsonPath("$[0].precioTotal").value(150000))
                .andExpect(jsonPath("$[0].producto.nombre").value("camisa deportiva"))
                .andExpect(jsonPath("$[0].producto.referencia").value("CAM01LN"))
                .andExpect(jsonPath("$[1].cantidad").value(4))
                .andExpect(jsonPath("$[1].precioTotal").value(280000))
                .andExpect(jsonPath("$[1].producto.nombre").value("pantalon deportivo"))
                .andExpect(jsonPath("$[1].producto.referencia").value("PAN0136G"))
                .andExpect(jsonPath("$[2].cantidad").value(5))
                .andExpect(jsonPath("$[2].precioTotal").value(225000))
                .andExpect(jsonPath("$[2].producto.nombre").value("pantaloneta casual"))
                .andExpect(jsonPath("$[2].producto.referencia").value("PAMCAS0134B"));
    }

}
