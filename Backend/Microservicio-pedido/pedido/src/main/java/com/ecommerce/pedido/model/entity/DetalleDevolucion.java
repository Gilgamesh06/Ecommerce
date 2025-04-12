package com.ecommerce.pedido.model.entity;

import jakarta.persistence.*;

@Entity(name = "detalle_devolucion")
public class DetalleDevolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "devolucion_id")
    private Devolucion devolucion;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;

    private String descripcion;

    // Constructor
    public DetalleDevolucion(){}
    public DetalleDevolucion(Devolucion devolucion, Producto producto,
                             Integer cantidad, String descripcion){
        this.devolucion = devolucion;
        this.producto = producto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    // Setters
    public void setDevolucion(Devolucion devolucion){this.devolucion = devolucion;}
    public void setProducto(Producto producto){this.producto = producto;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}

    // Getters
    public Long getId(){return this.id;}
    public Devolucion getDevolucion(){return this.devolucion;}
    public Producto getProducto(){return this.producto;}
    public Integer getCantidad(){return this.cantidad;}
    public String getDescripcion(){return this.descripcion;}
}
