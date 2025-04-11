package com.ecommerce.venta.service.connect;

import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InventarioClient {

    private final RestTemplate restTemplate;

    @Value("${microservicio.producto.base-url}")
    private String baseUrl;

    public InventarioClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<InventarioResponseDTO> getProducts(List<Long> productIds){
        String url = baseUrl + "/listar-productos"; // URL del endpoint sin los IDs

        // Definir el tipo de respuesta esperado (Lista de InventarioResponseDTO)
        ParameterizedTypeReference<List<InventarioResponseDTO>> responseType =
                new ParameterizedTypeReference<List<InventarioResponseDTO>>() {};
        ResponseEntity<List<InventarioResponseDTO>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(productIds), // Envía productIds en el cuerpo
                responseType
        );
        return responseEntity.getBody();
    }
}
