package com.ecommerce.venta;

import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.venta.model.dto.producto.ProductoInfoDTO;
import com.ecommerce.venta.service.component.ValidationProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValidationProducerTest {

    @InjectMocks
    private ValidationProduct validationProduct;

    public static List<InventarioResponseDTO>  getInventario(){
        ProductoInfoDTO producto1 = new ProductoInfoDTO("CAM01LN","camisa deportiva","L","negro","hombre","camisa",35000.0,50000.0);
        ProductoInfoDTO producto2 = new ProductoInfoDTO("PAN0136G","pantalon deportivo","36","gris","mujer","pantalon",45000.0,70000.0);
        ProductoInfoDTO producto3 = new ProductoInfoDTO("PAMCAS0134B","pantaloneta casual","34","blanco","hombre","pantalon",25000.0,45000.0);
        InventarioResponseDTO inventario1 =  new InventarioResponseDTO(20,producto1);
        InventarioResponseDTO inventario2 =  new InventarioResponseDTO(30,producto2);
        InventarioResponseDTO inventario3 =  new InventarioResponseDTO(40,producto3);

        List<InventarioResponseDTO> inventario = new ArrayList<>();
        inventario.add(inventario1);
        inventario.add(inventario2);
        inventario.add(inventario3);
        return inventario;
    }

    @Test
    public void validationProductTrueTest(){
        CarritoResponseDTO carrito1 = new CarritoResponseDTO(1L,20);
        CarritoResponseDTO carrito2 = new CarritoResponseDTO(2L,30);
        CarritoResponseDTO carrito3 = new CarritoResponseDTO(3L,40);
        List<CarritoResponseDTO> carrito = new ArrayList<>();
        carrito.add(carrito1);
        carrito.add(carrito2);
        carrito.add(carrito3);

        boolean condiccion = this.validationProduct.validarProductos(getInventario(),carrito);

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

        boolean condiccion = this.validationProduct.validarProductos(getInventario(),carrito);

        assertFalse(condiccion);
    }

    @Test
    public void setValidationProductNotSizeTest(){
        CarritoResponseDTO carrito1 = new CarritoResponseDTO(1L,40);
        CarritoResponseDTO carrito2 = new CarritoResponseDTO(2L,30);
        List<CarritoResponseDTO> carrito = new ArrayList<>();
        carrito.add(carrito1);
        carrito.add(carrito2);

        boolean condiccion = this.validationProduct.validarProductos(getInventario(),carrito);

        assertFalse(condiccion);
    }
}
