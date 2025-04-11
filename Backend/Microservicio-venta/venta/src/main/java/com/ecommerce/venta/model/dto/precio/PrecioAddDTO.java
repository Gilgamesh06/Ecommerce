package com.ecommerce.venta.model.dto.precio;

import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.entity.Producto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PrecioAddDTO {


    @PositiveOrZero
    private Double precioUnid;

    @PositiveOrZero
    private Double precioVenta;


    @NotNull
    private Producto producto;

    // Constructor
    public PrecioAddDTO(){}
    public PrecioAddDTO(Long productoId, Double precioUnid,Double precioVenta, LocalDateTime fecha, Producto producto){
        this.precioUnid = precioUnid;
        this.precioVenta = precioVenta;
        this.producto = producto;
    }

    // Setters
    public void setPrecioUnid(Double precioUnid){this.precioUnid = precioUnid;}
    public void setPrecioVenta(Double precioVenta){this.precioVenta = precioVenta;}
    public void setProducto(Producto producto){this.producto = producto;}

    // Getters
    public Double getPrecioUnid(){return this.precioUnid;}
    public Double getPrecioVenta(){return this.precioVenta;}
    public Producto getProducto(){return this.producto;}
}
