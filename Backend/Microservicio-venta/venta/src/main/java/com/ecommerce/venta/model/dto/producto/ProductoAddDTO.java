package com.ecommerce.venta.model.dto.producto;

import jakarta.validation.constraints.NotBlank;

public class ProductoAddDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String referencia;

    @NotBlank
    private String talla;

    @NotBlank
    private String color;


    @NotBlank
    private String subtipo;

    @NotBlank
    private String target;

    // Constructores
    public ProductoAddDTO(){}
    public ProductoAddDTO(String nombre, String referencia, String talla, String color,String subtipo, String target){
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
    public String getNombre(){return this.nombre;}
    public String getReferencia(){return this.referencia;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
    public String getSubtipo(){return this.subtipo;}
    public String getTarget(){return this.target;}
}
