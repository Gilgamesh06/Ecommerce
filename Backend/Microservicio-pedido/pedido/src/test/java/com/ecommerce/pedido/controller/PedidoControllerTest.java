package com.ecommerce.pedido.controller;

import com.ecommerce.pedido.TestDataProvider;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseCompleteDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseSimpleDTO;
import com.ecommerce.pedido.service.impl.PedidoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    // datos
    private TestDataProvider testDataProvider = new TestDataProvider();

    @Test
    public void getAllPedidosSimpleIsOkTest() throws Exception{

        // Datos que debe devolver el servicePedido
        List<PedidoResponseSimpleDTO> pedidoSimples = this.testDataProvider.getAllPedidoSimpleDTO();

        // Retorna el objeto pedidoSimples
        when(this.pedidoService.getAllPedidos()).thenReturn(pedidoSimples);

        mockMvc.perform(get("/api/v1/pedido/listar-pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].referencia").value(pedidoSimples.get(0).getReferencia()))
                .andExpect(jsonPath("$[0].estado").value(pedidoSimples.get(0).getEstado()))
                .andExpect(jsonPath("$[0].precioTotal").value(pedidoSimples.get(0).getPrecioTotal()));
    }

    @Test
    public void getAllPedidosSimpleIsNotFoundTest() throws Exception{

        // Retorna una lista vacia
        when(this.pedidoService.getAllPedidos()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/pedido/listar-pedidos"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listPedidoByReferenceIsOkTest() throws Exception{

        // Datos que debe retornar el pedidoService
        PedidoResponseCompleteDTO pedidoDTO = this.testDataProvider.getAllPedidCompleteResponse().getFirst();

        // Referencia
        String referencia = "REF-1";

        // Retorna el objeto que
        when(this.pedidoService.getPedidoComplete(eq(referencia))).thenReturn(Optional.of(pedidoDTO));

        mockMvc.perform(get("/api/v1/pedido/listar/{referencia}", referencia))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.referencia").value(pedidoDTO.getReferencia()))
                .andExpect(jsonPath("$.estado").value(pedidoDTO.getEstado()))
                .andExpect(jsonPath("$.precioTotal").value(pedidoDTO.getPrecioTotal()));
    }

    @Test
    public void listPedidoByReferenceIsNotFoundTest() throws Exception{

        // Referencia
        String referencia = "REF-1";

        // Retorna el objeto que
        when(this.pedidoService.getPedidoComplete(eq(referencia))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/pedido/listar/{referencia}", referencia))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllPedidosCompleteIsOkTest() throws Exception{

        // Datos que debe devolver el servicePedido
        List<PedidoResponseCompleteDTO> pedidoComplete = this.testDataProvider.getAllPedidCompleteResponse();

        // Retorna el objeto pedidoComplete
        when(this.pedidoService.getAllPedidoComplete()).thenReturn(pedidoComplete);

        mockMvc.perform(get("/api/v1/pedido/listar-completo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].referencia").value(pedidoComplete.get(0).getReferencia()))
                .andExpect(jsonPath("$[0].estado").value(pedidoComplete.get(0).getEstado()))
                .andExpect(jsonPath("$[0].precioTotal").value(pedidoComplete.get(0).getPrecioTotal()))
                .andExpect(jsonPath("$[0].detallesPedido[0].producto.nombre").value("camisa deportiva"))
                .andExpect(jsonPath("$[1].referencia").value(pedidoComplete.get(1).getReferencia()))
                .andExpect(jsonPath("$[1].estado").value(pedidoComplete.get(1).getEstado()))
                .andExpect(jsonPath("$[1].precioTotal").value(pedidoComplete.get(1).getPrecioTotal()))
                .andExpect(jsonPath("$[2].referencia").value(pedidoComplete.get(2).getReferencia()))
                .andExpect(jsonPath("$[2].estado").value(pedidoComplete.get(2).getEstado()))
                .andExpect(jsonPath("$[2].precioTotal").value(pedidoComplete.get(2).getPrecioTotal()));
    }

    @Test
    public void getAllPedidosCompleteIsNotFoundTest() throws Exception{


        // Retorna una lista Vacia
        when(this.pedidoService.getAllPedidoComplete()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/pedido/listar-completo"))
                .andExpect(status().isNotFound());
    }

}
