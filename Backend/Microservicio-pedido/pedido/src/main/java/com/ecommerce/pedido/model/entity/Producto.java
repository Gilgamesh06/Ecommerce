package com.ecommerce.pedido.model.entity;

import jakarta.persistence.*;

@Entity(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String referencia;

    @Column(nullable = false)
    private String talla;

    @Column(nullable = false)
    private String color;

    // Constructores
    public Producto(){}

    public Producto(String referencia, String talla, String color){
        this.referencia = referencia;
        this.talla = talla;
        this.color = color;
    }

    // Setters
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.color = color;}

    // Getters
    public String getReferencia(){return this.referencia;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
}
