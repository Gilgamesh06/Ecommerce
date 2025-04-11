package com.ecommerce.inventario.model.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductoInfoDTO {

    @NotBlank
    private String referencia;

    @NotBlank
    private String nombre;

    @NotBlank
    private String talla;

    @NotBlank
    private String subtipo;

    @NotBlank
    private String target;

    @NotBlank
    private String color;

    @NotBlank
    private Double precioUnid;

    @NotBlank
    private Double precioVenta;

    // Constructor
    public ProductoInfoDTO(){}
    public ProductoInfoDTO(String referencia, String nombre, String talla, String color, String target, String subtipo, Double precioUnid, Double precioVenta) {
        this.referencia = referencia;
        this.nombre = nombre;
        this.talla = talla;
        this.color = color;
        this.target = target;
        this.subtipo = subtipo;
        this.precioUnid = precioUnid;
        this.precioVenta = precioVenta;
    }

    // Setters
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setSubtipo(String subtipo){this.subtipo = subtipo;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.color = color;}
    public void setPrecioUnid(Double precioUnid){this.precioUnid = precioUnid;}
    public void setPrecioVenta(Double precioVenta){this.precioVenta = precioVenta;}
    public void setTarget(String target){this.target = target;}

    // Getters
    public String getReferencia(){return this.referencia;}
    public String getNombre(){return nombre;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
    public String getSubtipo(){return this.subtipo;}
    public Double getPrecioUnid(){return this.precioUnid;}
    public Double getPrecioVenta(){return this.precioVenta;}
    public String getTarget(){return this.target;}
}




