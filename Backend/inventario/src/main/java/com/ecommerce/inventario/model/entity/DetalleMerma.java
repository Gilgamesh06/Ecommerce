package com.ecommerce.inventario.model.entity;

import jakarta.persistence.*;

@Entity(name = "detalle_merma")
public class DetalleMerma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name= "merma_id")
    private Merma merma;

    private String descripcion;

    // Constructores
    public DetalleMerma(){}
    public DetalleMerma(Producto producto, Merma merma, String descripcion){
        this.producto = producto;
        this.merma = merma;
        this.descripcion = descripcion;
    }

    // Setters
    public void setProducto(Producto producto){this.producto = producto;}
    public void setMerma(Merma merma){this.merma = merma;}
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}

    // Getters
    public Long getId(){return this.id;}
    public Producto getProducto(){return this.producto;}
    public Merma getMerma(){return this.merma;}
    public String getDescripcion(){return this.descripcion;}
}
