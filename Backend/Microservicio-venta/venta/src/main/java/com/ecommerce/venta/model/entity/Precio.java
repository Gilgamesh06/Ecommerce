package com.ecommerce.venta.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name="precio")
public class Precio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="precio_unid")
    private Double precioUnid;

    @Column(name = "precio_venta")
    private Double precioVenta;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;



    // Constructores
    public Precio(){}
    public Precio(Double precioUnid, Double precioVenta, LocalDateTime fecha, Producto producto){
        this.precioUnid = precioUnid;
        this.precioVenta = precioVenta;
        this.fecha = fecha;
        this.producto = producto;
    }

    // Setters
    public void setPrecioUnid(Double precioUnid){this.precioUnid = precioUnid;}
    public void setPrecioVenta(Double precioVenta){this.precioVenta = precioVenta;}
    public void setFecha(LocalDateTime fecha){this.fecha = fecha;}
    public void setProducto(Producto producto){this.producto = producto;}

    // Getters
    public Long getId(){return this.id;}
    public Double getPrecioUnid(){return this.precioUnid;}
    public Double getPrecioVenta(){return this.precioVenta;}
    public LocalDateTime getFecha(){return this.fecha;}
    public Producto getProducto(){return this.producto;}
}
