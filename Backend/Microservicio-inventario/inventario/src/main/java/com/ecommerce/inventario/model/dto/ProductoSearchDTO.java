package com.ecommerce.inventario.model.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductoSearchDTO {

    @NotBlank
    private String referencia;

    @NotBlank
    private String talla;

    @NotBlank
    private String color;

    // Constructores
    public ProductoSearchDTO(){}
    public ProductoSearchDTO(String referencia, String talla, String color){
        this.referencia = referencia;
        this.talla = talla;
        this.color = color;
    }

    // Setters
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.color = color;}

    // Getters
    public String getReferencia(){return  this.referencia;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
}

