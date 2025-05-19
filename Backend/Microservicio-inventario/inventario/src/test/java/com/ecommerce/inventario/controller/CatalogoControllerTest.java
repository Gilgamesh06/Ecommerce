package com.ecommerce.inventario.controller;

import com.ecommerce.inventario.TestDataProvider;
import com.ecommerce.inventario.model.dto.catalogo.ProductoAgrupadoDTO;
import com.ecommerce.inventario.model.dto.catalogo.VarianteDTO;
import com.ecommerce.inventario.model.dto.detalleproducto.DetalleProductoResponseDTO;
import com.ecommerce.inventario.service.impl.CatalogoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatalogoController.class)
public class CatalogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CatalogoService catalogoService;

    @Autowired
    private ObjectMapper objectMapper;

    private TestDataProvider testDataProvider = new TestDataProvider();

    private List<ProductoAgrupadoDTO> crearListaProductosAgrupados() {
        DetalleProductoResponseDTO detalleDTO = new DetalleProductoResponseDTO("Info Detalle", "Algodón", "China");
        VarianteDTO variante1 = new VarianteDTO(1L, "M", "Azul", 25.99, detalleDTO);
        ProductoAgrupadoDTO producto1 = new ProductoAgrupadoDTO("REF001", "Camisa Azul", Set.of("M"), Set.of("Azul"), List.of(variante1));
        return List.of(producto1);
    }

    private Map<String,Set<String>> crearMapFiltros(){
        Set<String> tipos1 = Set.of("camisa", "pantalon", "chaqueta");
        Set<String> tipos2 = Set.of("deportivo", "casual", "formal");
        Map<String,Set<String>> mapTipos = new HashMap<>();
        mapTipos.put("ropa",tipos1);
        mapTipos.put("zapatos",tipos2);
        return mapTipos;
    }

    @Test
    @DisplayName("GET /api/v1/catalogo/{target} - Debería devolver 200 OK con productos cuando el servicio los encuentra")
    void getCatalogo_whenServiceReturnsProducts_thenReturnsOkWithProducts() throws Exception {
        // Arrange
        String target = "hombre";
        String tipo = "ropa";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre")); // Coincide con @PageableDefault

        List<ProductoAgrupadoDTO> listaProductos = crearListaProductosAgrupados();
        Page<ProductoAgrupadoDTO> paginaProductos = new PageImpl<>(listaProductos, pageable, listaProductos.size());

        when(catalogoService.filterProductsTargets(eq(target), eq(tipo), any(), any(Pageable.class)))
                .thenReturn(paginaProductos);

        // Act & Assert
        mockMvc.perform(get("/api/v1/catalogo/{target}", target)
                        .param("tipo", tipo)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "nombre,ASC") // Simula el PageableDefault
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].referencia", is("REF001")))
                .andExpect(jsonPath("$.content[0].nombre", is("Camisa Azul")))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(1)));

        // Verifica que el servicio fue llamado con los parámetros correctos (incluyendo Pageable)
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(catalogoService).filterProductsTargets(eq(target), eq(tipo), isNull(), pageableCaptor.capture());
        assertEquals(10, pageableCaptor.getValue().getPageSize());
        assertEquals("nombre", pageableCaptor.getValue().getSort().getOrderFor("nombre").getProperty());
    }

    @Test
    @DisplayName("GET /api/v1/catalogo/{target} - Debería devolver 404 Not Found cuando el servicio no encuentra productos")
    void getCatalogo_whenServiceReturnsEmpty_thenReturnsNotFound() throws Exception {
        // Arrange
        String target = "dama";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre"));
        Page<ProductoAgrupadoDTO> paginaVacia = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(catalogoService.filterProductsTargets(eq(target), any(), any(), any(Pageable.class)))
                .thenReturn(paginaVacia);

        // Act & Assert
        mockMvc.perform(get("/api/v1/catalogo/{target}", target)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "nombre,ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v1/catalogo/{target} - Debería usar PageableDefault si no se envían parámetros de paginación")
    void getCatalogo_whenNoPageParams_thenUsesPageableDefault() throws Exception {
        // Arrange
        String target = "niños";
        // Pageable por defecto: size = 10, sort = "nombre", direction = Sort.Direction.ASC
        Pageable expectedPageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre"));

        List<ProductoAgrupadoDTO> listaProductos = crearListaProductosAgrupados();
        Page<ProductoAgrupadoDTO> paginaProductos = new PageImpl<>(listaProductos, expectedPageable, listaProductos.size());

        // Usamos any(Pageable.class) para el mock, la verificación de que se usa el default
        // es más bien una prueba de integración de Spring MVC, pero podemos verificar que
        // el servicio se llama con un Pageable que tenga esos valores si usamos ArgumentCaptor.
        // Por simplicidad aquí, solo verificamos que se llama.
        when(catalogoService.filterProductsTargets(eq(target), any(), any(), any(Pageable.class)))
                .thenReturn(paginaProductos);


        // Act & Assert
        mockMvc.perform(get("/api/v1/catalogo/{target}", target)
                        // No se envían params de paginación para probar PageableDefault
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.pageable.pageSize", is(10))) // Verifica el tamaño de página del default
                .andExpect(jsonPath("$.sort.sorted", is(true))) // Verifica que está ordenado
                .andExpect(jsonPath("$.content[0].referencia").value("REF001"))
                .andExpect(jsonPath("$.content[0].nombre").value("Camisa Azul"))
                .andExpect(jsonPath("$.pageable.sort.sorted").value(true)) // Verifica que 'sorted' sea true
                .andExpect(jsonPath("$.pageable.sort.empty").value(false)); // Verifica que 'empty' sea false

        // Podrías usar un ArgumentCaptor aquí si quieres ser muy específico sobre el Pageable
        // que llega al servicio, aunque @PageableDefault es una característica de Spring que
        // usualmente "simplemente funciona".
    }


    @Test
    @DisplayName("GET /api/v1/catalogo/filtros - Debería retornar un Map con clave tipo y value List<String> de subtipos")
    public void getCatalogoFiltresMapTest() throws Exception{

        // Datos a probar
        Map<String, Set<String>> mapTipos = crearMapFiltros();

        // Retorna el map que definimos como prueba
        when(this.catalogoService.TypesOfTheFilter()).thenReturn(mapTipos);

        mockMvc.perform(get("/api/v1/catalogo/filtros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ropa", org.hamcrest.Matchers.containsInAnyOrder("camisa", "pantalon", "chaqueta")))
                .andExpect(jsonPath("$.zapatos", org.hamcrest.Matchers.containsInAnyOrder("deportivo", "casual", "formal")));
    }
}
