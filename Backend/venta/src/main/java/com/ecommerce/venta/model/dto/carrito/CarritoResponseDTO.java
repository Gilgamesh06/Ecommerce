package com.ecommerce.venta.model.dto.carrito;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CarritoResponseDTO {

    @NotNull
    private Long productoId;

    @Positive
    private Integer cantidad;

    // Constructor
    public CarritoResponseDTO(){}
    public CarritoResponseDTO(Long productoId, Integer cantidad){
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    // Setter
    public void setProductoId(Long productoId){this.productoId = productoId;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}

    // Getters
    public Long getProductoId(){return this.productoId;}
    public Integer getCantidad(){return this.cantidad;}
}
