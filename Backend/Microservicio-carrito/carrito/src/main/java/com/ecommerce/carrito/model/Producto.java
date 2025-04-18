package com.ecommerce.carrito.model;

import java.io.Serializable;

public class Producto implements Serializable {

    private String nombre;
    private String talla;
    private Double precio;
    private Integer cantidad;

    // Constructor
    public Producto(){}
    public Producto(String nombre, String talla, Double precio, Integer cantidad ){
        this.nombre = nombre;
        this.talla = talla;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Setters
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setTalla(String talla){this.talla = talla;}
    public void setPrecio(Double precio){this.precio = precio;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}

    // Getters
    public String getNombre(){return this.nombre;}
    public String getTalla(){return this.talla;}
    public Double getPrecio(){return this.precio;}
    public Integer getCantidad(){return this.cantidad;}
}
