package com.ecommerce.venta.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String referencia;
    private String talla;
    private String color;
    private String subtipo;
    private String target;

    // Constructores
    public Producto(){}
    public Producto(String nombre, String referencia, String talla, String color,String tipo ,String subtipo, String target){
        this.nombre = nombre;
        this.referencia = referencia;
        this.talla = talla;
        this.color = color;
        this.subtipo = subtipo;
        this.target = target;
    }

    // Setters
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.color = color;}
    public void setSubtipo(String subtipo){this.subtipo = subtipo;}
    public void setTarget(String target){this.target = target;}

    // Getters
    public Long getId(){return this.id;}
    public String getNombre(){return this.nombre;}
    public String getReferencia(){return this.referencia;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
    public String getSubtipo(){return this.subtipo;}
    public String getTarget(){return this.target;}
}
