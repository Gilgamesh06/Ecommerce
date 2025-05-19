package com.ecommerce.venta.controller;

import com.ecommerce.venta.TestDataProvider;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.venta.model.dto.venta.VentaResponseDTO;
import com.ecommerce.venta.service.impl.VentaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(VentaController.class)
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VentaService ventaService;

    // Instancimiento de datos
    private TestDataProvider testDataProvider = new TestDataProvider();

    @Test
    public void getAllVentasIsOkTest() throws Exception{

        // Datos que debe devolver el service Venta
        List<VentaResponseDTO> ventas = this.testDataProvider.getAllVentaResponseDTO();

        when(this.ventaService.getAllVentas()).thenReturn(ventas);

        mockMvc.perform(get("/api/v1/ventas/listar-ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].referencia").value("0001"))
                .andExpect(jsonPath("$[0].estado").value("Exitosa"))
                .andExpect(jsonPath("$[0].valorVenta").value(ventas.get(0).getValorVenta()));
    }

    @Test
    public void getAllVentasIsNotFoundTest() throws Exception{

        // Datos que debe devolver el service Venta
        List<VentaResponseDTO> ventas = new ArrayList<>();

        when(this.ventaService.getAllVentas()).thenReturn(ventas);

        mockMvc.perform(get("/api/v1/ventas/listar-ventas"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getVentaByReferenceIsOkTest() throws Exception{

        // Datos que debe devolver el service Venta
        List<VentaResponseDTO> ventas = this.testDataProvider.getAllVentaResponseDTO();
        // Referencia
        String referencia = ventas.getFirst().getReferencia();

        // Al ingresar la referecia 0001 retorna un optional Venta
        when(this.ventaService.getVentaByReference(eq(referencia))).thenReturn(Optional.of(ventas.getFirst()));

        mockMvc.perform(get("/api/v1/ventas/obtener-venta/{referencia}", referencia))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.referencia").value("0001"))
                .andExpect(jsonPath("$.estado").value("Exitosa"))
                .andExpect(jsonPath("$.valorVenta").value(ventas.getFirst().getValorVenta()));
    }

    @Test
    public void getVentaByReferenceNoFoundTest() throws Exception{

        // Referencia
        String referencia = "0001";

        // Al ingresar la referecia 0001 retorna un optional empty
        when(this.ventaService.getVentaByReference(eq(referencia))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/ventas/obtener-venta/{referencia}", referencia))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getDetalleVentaByReferenceIsOkTest() throws Exception{

        // Datos que debe devolver el service Venta
        List<VentaResponseDTO> ventas = this.testDataProvider.getAllVentaResponseDTO();
        // Referencia
        String referencia = ventas.getFirst().getReferencia();
        List<DetalleVentaResponseDTO> detalles = ventas.getFirst().getDetalleVenta();
        // Al ingresar la referecia 0001 retorna un optional Venta
        when(this.ventaService.obtenerDetalles(eq(referencia))).thenReturn(detalles);

        mockMvc.perform(get("/api/v1/ventas/obtener-detalles/{referencia}", referencia))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidad").value(detalles.getFirst().getCantidad()))
                .andExpect(jsonPath("$[0].precioTotal").value(detalles.getFirst().getPrecioTotal()))
                .andExpect(jsonPath("$[0].producto.nombre").value(detalles.getFirst().getProducto().getNombre()));
    }

    @Test
    public void getDetalleVentaByReferenceIsNotFoundTest() throws Exception{

        // Referencia
        String referencia = "0001";

        // Al ingresar la referecia 0001 retorna una lista vacia
        when(this.ventaService.obtenerDetalles(eq(referencia))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/ventas/obtener-detalles/{referencia}", referencia))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllDetalleVentaByReferenceIsOKTest() throws Exception{

        List<List<DetalleVentaResponseDTO>> detalleVentas = new ArrayList<>();

        String referenciaSend = "[ \"0001\",\"0002\"]";

        List<DetalleVentaResponseDTO> detalles1 = this.testDataProvider.getDetalleVentaResponse();
        List<DetalleVentaResponseDTO> detalles2 = List.of(detalles1.get(0), detalles1.get(1));
        detalleVentas.add(detalles1);
        detalleVentas.add(detalles2);

        // Al ingresar la referecia 0001 retorna una lista de listas
        when(this.ventaService.obtenerAllDetalles(anyList())).thenReturn(detalleVentas);

        mockMvc.perform(get("/api/v1/ventas/obtener-detalles/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(referenciaSend))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].[0].cantidad").value(detalles1.getFirst().getCantidad()))
                .andExpect(jsonPath("$[0].[0].precioTotal").value(detalles1.getFirst().getPrecioTotal()))
                .andExpect(jsonPath("$[0].[0].producto.nombre").value(detalles1.getFirst().getProducto().getNombre()));
    }

    @Test
    public void getAllDetalleVentaByReferenceIsNotFoundTest() throws Exception{


        String referenciaSend = "[ \"0001\",\"0002\"]";

        // Al ingresar la referecia 0001 retorna una lista vacia
        when(this.ventaService.obtenerAllDetalles(anyList())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/ventas/obtener-detalles/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(referenciaSend))
                .andExpect(status().isNotFound());
    }


    @Test
    public void addVentaIsCreatedTest() throws Exception{

        // Datos que debe recibir el EndPoint
        String carritoSend = "[{\"productoId\":1,\"cantidad\":3},{\"productoId\":2,\"cantidad\":4},{\"productoId\":3,\"cantidad\":5}]";

        // Datos que debe devolver el service Venta
        VentaResponseDTO venta = this.testDataProvider.getAllVentaResponseDTO().getFirst();

        // Recibe cualquier lista y retorna un Objeto de tipo VentaResponseDTO
        when(this.ventaService.addElement(anyList())).thenReturn(Optional.of(venta));

        mockMvc.perform(post("/api/v1/ventas/realiza-venta")
                .contentType(MediaType.APPLICATION_JSON)
                    .content(carritoSend))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("referencia").value(venta.getReferencia()))
                .andExpect(jsonPath("estado").value(venta.getEstado()));
    }

    @Test
    public void addVentaIsUnprocessableEntityTest() throws Exception{

        // Datos que debe recibir el EndPoint
        String carritoSend = "[{\"productoId\":1,\"cantidad\":3},{\"productoId\":2,\"cantidad\":4},{\"productoId\":3,\"cantidad\":5}]";

        // Recibe cualquier lista y retorna un Optional Empty
        when(this.ventaService.addElement(anyList())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/ventas/realiza-venta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carritoSend))
                .andExpect(status().isUnprocessableEntity());
    }
}
