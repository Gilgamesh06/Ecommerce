package com.ecommerce.inventario.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "detalle_producto")
public class DetalleProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String composicion;
    private String pais;

    // Constructores
    public DetalleProducto(){}
    public DetalleProducto(String descripcion, String composicion, String pais){
        this.descripcion = descripcion;
        this.composicion = composicion;
        this.pais = pais;
    }

    // Setters
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}
    public void setComposicion(String composicion){this.composicion = composicion;}
    public void setPais(String pais){this.pais = pais;}

    // Getters
    public Long getId(){return this.id;}
    public String getDescripcion(){return this.descripcion;}
    public String getComposicion(){return this.composicion;}
    public String getPais(){return this.pais;}
}
