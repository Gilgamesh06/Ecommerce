package com.ecommerce.carrito.model.dto.detalleventa;

import com.ecommerce.carrito.model.dto.producto.ProductoResponseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DetalleVentaResponseDTO {

    @NotNull
    private ProductoResponseDTO producto;

    @Positive
    private Integer cantidad;

    @Positive
    private Double precioTotal;

    public DetalleVentaResponseDTO(){}
    public DetalleVentaResponseDTO(ProductoResponseDTO producto, Integer cantidad, Double precioTotal){
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    // Setters
    public void setProducto(ProductoResponseDTO producto){this.producto = producto;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}
    public void setPrecioTotal(Double precioTotal){this.precioTotal = precioTotal;}

    // Getters
    public ProductoResponseDTO getProducto(){return this.producto;}
    public Integer getCantidad(){return this.cantidad;}
    public Double getPrecioTotal(){return this.precioTotal;}
}