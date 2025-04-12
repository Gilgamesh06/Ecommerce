package com.ecommerce.venta.model.entity;

import jakarta.persistence.*;


@Entity(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;

    @Column(name ="precio_total")
    private Double precioTotal;

    // Constructor
    public DetalleVenta(){}
    public DetalleVenta(Venta venta, Producto producto, Integer cantidad, Double precioTotal){
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    // Setters
    public void setVenta(Venta venta){this.venta = venta;}
    public void setProducto(Producto producto){this.producto = producto;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}
    public void setPrecioTotal(Double precioTotal){this.precioTotal = precioTotal;}

    // Getters
    public Long getId(){return this.id;}
    public Venta getVenta(){return this.venta;}
    public Producto getProducto(){return this.producto;}
    public Integer getCantidad(){return this.cantidad;}
    public Double getPrecioTotal(){return this.precioTotal;}
}
