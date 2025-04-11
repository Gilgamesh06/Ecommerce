package com.ecommerce.venta.model.entity;

import jakarta.persistence.*;

@Entity(name = "detalle_rembolso")
public class DetalleRembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rembolso_id")
    private Rembolso rembolso;

    @OneToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;

    @Column(nullable = false)
    private String descripcion;

    // Constructor
    public DetalleRembolso(){}

    public DetalleRembolso(Rembolso rembolso, Producto producto, Integer cantidad, String descripcion){
        this.rembolso = rembolso;
        this.producto = producto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    // Setters
    private void setRembolso(Rembolso rembolso){this.rembolso = rembolso;}
    private void setProducto(Producto producto){this.producto = producto;}
    private void setCantidad(Integer cantidad){this.cantidad = cantidad;}
    private void setDescripcion(String descripcion){this.descripcion = descripcion;}

    // Getters
    private Long getId(){return this.id;}
    private Rembolso getRembolso(){return this.rembolso;}
    private Producto getProducto(){return this.producto;}
    private Integer getCantidad(){return this.cantidad;}
    private String getDescripcion(){return this.descripcion;}
}
