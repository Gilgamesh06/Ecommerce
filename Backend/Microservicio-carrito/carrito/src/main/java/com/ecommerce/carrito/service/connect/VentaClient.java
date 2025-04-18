package com.ecommerce.carrito.service.connect;

import com.ecommerce.carrito.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.carrito.model.dto.venta.VentaResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class VentaClient {

    private final RestTemplate restTemplate;

    @Value("${microservicio.venta.base-url}")
    private String baseUrl;

    public VentaClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public VentaResponseDTO makeSale(List<CarritoResponseDTO> carritoResponseDTOs){
        String url = baseUrl +"/realiza-venta";

        ParameterizedTypeReference<VentaResponseDTO> responseType =
                new ParameterizedTypeReference<VentaResponseDTO>() {};
        ResponseEntity<VentaResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(carritoResponseDTOs),
                responseType
        );
        return responseEntity.getBody();
    }

}
