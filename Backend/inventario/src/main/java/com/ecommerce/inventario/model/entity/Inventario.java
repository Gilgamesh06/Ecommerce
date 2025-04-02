package com.ecommerce.inventario.model.entity;

import jakarta.persistence.*;

@Entity(name="inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="producto_id")
    private Producto producto;

    private Integer cantidad;

    // Constructor
    public Inventario(){}
    public Inventario(Producto producto, Integer cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Setters
    public void setProducto(Producto producto){this.producto = producto;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}

    //  Getters

    public Long getId() {return id;}
    public Producto getProducto(){return this.producto;}
    public Integer getCantidad(){return this.cantidad;}
}
