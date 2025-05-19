package com.ecommerce.inventario.controller;

import com.ecommerce.inventario.TestDataProvider;
import com.ecommerce.inventario.model.dto.inventario.InventarioAddDTO;
import com.ecommerce.inventario.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoSearchDTO;
import com.ecommerce.inventario.service.impl.InventarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventarioService inventarioService;

    private TestDataProvider testDataProvider = new TestDataProvider();



    @Test
    public void getAllInventarioIsOkTest() throws Exception{

        // Datos que debe devolver el service inventario
        List<InventarioResponseDTO> inventario = List.of(this.testDataProvider.getInventarioResponseDTO());

        when(this.inventarioService.findAll()).thenReturn(inventario);

        mockMvc.perform(get("/api/v1/inventario/listar-todos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productoInfoDTO.nombre").value(inventario.getFirst().getProductoInfoDTO().getNombre()))
                .andExpect(jsonPath("$[0].productoInfoDTO.referencia").value(inventario.getFirst().getProductoInfoDTO().getReferencia()))
                .andExpect(jsonPath("$[0].productoInfoDTO.talla").value(inventario.getFirst().getProductoInfoDTO().getTalla()))
                .andExpect(jsonPath("$[0].productoInfoDTO.color").value(inventario.getFirst().getProductoInfoDTO().getColor()));
    }

    @Test
    public void getAllInventarioIsNotFoundTest() throws Exception{

        // Datos que debe devolver el service inventario
        List<InventarioResponseDTO> inventario = new ArrayList<>();

        when(this.inventarioService.findAll()).thenReturn(inventario);

        mockMvc.perform(get("/api/v1/inventario/listar-todos"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getInventarioByParametersIsOkTest() throws Exception{

        // Datos que debe devolver el service inventario
        InventarioResponseDTO inventario = this.testDataProvider.getInventarioResponseDTO();

        String productoSearchSend = "{\"referencia\":\"CAM01LN\",\"talla\":\"L\",\"color\":\"negro\"}";
        when(this.inventarioService.findByAtributes(any(ProductoSearchDTO.class))).thenReturn(Optional.of(inventario));

        mockMvc.perform(get("/api/v1/inventario/listar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoSearchSend))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(inventario.getCantidad()))
                .andExpect(jsonPath("$.productoInfoDTO.nombre").value(inventario.getProductoInfoDTO().getNombre()))
                .andExpect(jsonPath("$.productoInfoDTO.referencia").value(inventario.getProductoInfoDTO().getReferencia()))
                .andExpect(jsonPath("$.productoInfoDTO.talla").value(inventario.getProductoInfoDTO().getTalla()))
                .andExpect(jsonPath("$.productoInfoDTO.color").value(inventario.getProductoInfoDTO().getColor()));
    }

    @Test
    public void getInventarioByParametersIsNotFoundTest() throws Exception{

        String productoSearchSend = "{\"referencia\":\"CAM01LN\",\"talla\":\"L\",\"color\":\"negro\"}";

        when(this.inventarioService.findByAtributes(any(ProductoSearchDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/inventario/listar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoSearchSend))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createdInventarioIsOkTest() throws Exception{

        // Datos que debe devolver el service inventario
        InventarioResponseDTO inventario = this.testDataProvider.getInventarioResponseDTO();

        String productoSearchSend = this.testDataProvider.getInventarioAddDTOJson();

        System.out.println("JSON Generado: " + productoSearchSend);

        when(this.inventarioService.addElement(any(InventarioAddDTO.class))).thenReturn(inventario);

        mockMvc.perform(post("/api/v1/inventario/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoSearchSend))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cantidad").value(inventario.getCantidad()))
                .andExpect(jsonPath("$.productoInfoDTO.nombre").value(inventario.getProductoInfoDTO().getNombre()))
                .andExpect(jsonPath("$.productoInfoDTO.referencia").value(inventario.getProductoInfoDTO().getReferencia()))
                .andExpect(jsonPath("$.productoInfoDTO.talla").value(inventario.getProductoInfoDTO().getTalla()))
                .andExpect(jsonPath("$.productoInfoDTO.color").value(inventario.getProductoInfoDTO().getColor()));
    }
}
