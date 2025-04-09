package com.ecommerce.venta.service.component;

import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationProduct {

    public Boolean validarProductos(List<InventarioResponseDTO> productos, List<CarritoResponseDTO> carrito){
        boolean cantidad = false;
        if(productos.size() == carrito.size()){
            for(int i =0 ; i< carrito.size(); i++){
                if(productos.get(i).getCantidad() >= carrito.get(i).getCantidad() ){
                    cantidad = true;
                }
                else{
                    return false;
                }
            }
        }
        return cantidad;
    }
}
