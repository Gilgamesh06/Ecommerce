package com.ecommerce.inventario.model.dto;

import com.ecommerce.inventario.model.entity.Producto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class InventarioResponseDTO {

    @PositiveOrZero
    private Integer cantidad;

    @NotNull
    private ProductoInfoDTO productoInfoDTO;

    public InventarioResponseDTO(){}
    public InventarioResponseDTO(Integer cantidad, ProductoInfoDTO productoInfoDTO){
        this.cantidad = cantidad;
        this.productoInfoDTO = productoInfoDTO;
    }

    // Setters
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}
    public void setProductoInfoDTO(ProductoInfoDTO productoInfoDTO){this.productoInfoDTO = productoInfoDTO;}

    // Getters
    public Integer getCantidad(){return cantidad;}
    public ProductoInfoDTO getProductoInfoDTO(){return this.productoInfoDTO;}

}
