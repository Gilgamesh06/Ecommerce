package com.ecommerce.venta.service;

import com.ecommerce.venta.TestDataProvider;
import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;

import com.ecommerce.venta.service.component.ValidationProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValidationProducerTest {

    // Instancimiento de datos
    private TestDataProvider testDataProvider = new TestDataProvider();

    @InjectMocks
    private ValidationProduct validationProduct;


    @Test
    public void validationProductTrueTest(){
        CarritoResponseDTO carrito1 = new CarritoResponseDTO(1L,20);
        CarritoResponseDTO carrito2 = new CarritoResponseDTO(2L,30);
        CarritoResponseDTO carrito3 = new CarritoResponseDTO(3L,40);
        List<CarritoResponseDTO> carrito = new ArrayList<>();
        carrito.add(carrito1);
        carrito.add(carrito2);
        carrito.add(carrito3);

        boolean condiccion = this.validationProduct.validarProductos(this.testDataProvider.getInventario(),carrito);

        assertTrue(condiccion);
    }

    @Test
    public void validationProductFalseTest(){
        CarritoResponseDTO carrito1 = new CarritoResponseDTO(1L,40);
        CarritoResponseDTO carrito2 = new CarritoResponseDTO(2L,30);
        CarritoResponseDTO carrito3 = new CarritoResponseDTO(3L,40);
        List<CarritoResponseDTO> carrito = new ArrayList<>();
        carrito.add(carrito1);
        carrito.add(carrito2);
        carrito.add(carrito3);

        boolean condiccion = this.validationProduct.validarProductos(this.testDataProvider.getInventario(),carrito);

        assertFalse(condiccion);
    }

    @Test
    public void setValidationProductNotSizeTest(){
        CarritoResponseDTO carrito1 = new CarritoResponseDTO(1L,40);
        CarritoResponseDTO carrito2 = new CarritoResponseDTO(2L,30);
        List<CarritoResponseDTO> carrito = new ArrayList<>();
        carrito.add(carrito1);
        carrito.add(carrito2);

        boolean condiccion = this.validationProduct.validarProductos(this.testDataProvider.getInventario(),carrito);

        assertFalse(condiccion);
    }
}
