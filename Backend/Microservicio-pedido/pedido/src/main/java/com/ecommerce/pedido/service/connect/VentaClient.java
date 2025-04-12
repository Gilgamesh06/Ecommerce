package com.ecommerce.pedido.service.connect;

import com.ecommerce.pedido.model.dto.detalleventa.DetalleVentaResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class VentaClient {

    private final RestTemplate restTemplate;

    @Value("${microservicio.venta.base-url}")
    private String baseUrl;

    public VentaClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DetalleVentaResponseDTO> getDetallePedido(String referencia) {
        // Construir la URL con la referencia como parte del path
        String url = baseUrl + "/obtener-detalles/" + referencia; // Agregar la referencia a la URL

        // Definir el tipo de respuesta esperado (Lista de DetalleVentaResponseDTO)
        ParameterizedTypeReference<List<DetalleVentaResponseDTO>> responseType =
                new ParameterizedTypeReference<List<DetalleVentaResponseDTO>>() {
                };

        ResponseEntity<List<DetalleVentaResponseDTO>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // No se necesita cuerpo para una solicitud GET
                responseType
        );
        return responseEntity.getBody();
    }

    public List<List<DetalleVentaResponseDTO>> getAllDetallePedido(List<String> referencia) {
        String url = baseUrl + "/obtener-detalles/all"; // URL del endpoint sin los IDs

        // Definir el tipo de respuesta esperado (Lista de InventarioResponseDTO)
        ParameterizedTypeReference<List<List<DetalleVentaResponseDTO>>> responseType =
                new ParameterizedTypeReference<List<List<DetalleVentaResponseDTO>>>() {
                };
        ResponseEntity<List<List<DetalleVentaResponseDTO>>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new org.springframework.http.HttpEntity<>(referencia), // Envía la referencia en el cuerpo
                responseType
        );
        return responseEntity.getBody();
    }
}
