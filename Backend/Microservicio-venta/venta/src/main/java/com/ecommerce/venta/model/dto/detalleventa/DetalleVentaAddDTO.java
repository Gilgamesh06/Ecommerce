package com.ecommerce.venta.model.dto.detalleventa;

import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.model.entity.Venta;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public class DetalleVentaAddDTO {

    private Venta venta;

    private Producto producto;

    private Integer cantidad;


    // Constructor
    public DetalleVentaAddDTO(){}
    public DetalleVentaAddDTO(Venta venta, Producto producto, Integer cantidad){
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Setters
    public void setVenta(Venta venta){this.venta = venta;}
    public void setProducto(Producto producto){this.producto = producto;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}


    // Getters
    public Venta getVenta(){return this.venta;}
    public Producto getProducto(){return this.producto;}
    public Integer getCantidad(){return this.cantidad;}

}


