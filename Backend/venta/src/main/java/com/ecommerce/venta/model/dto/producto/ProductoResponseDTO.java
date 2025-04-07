package com.ecommerce.venta.model.dto.producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductoResponseDTO {
    @NotBlank
    private String nombre;

    @NotBlank
    private String referencia;

    @NotBlank
    private String talla;

    @NotBlank
    private String color;



    // Constructores
    public ProductoResponseDTO(){}
    public ProductoResponseDTO(String nombre, String referencia, String talla, String color){
        this.nombre = nombre;
        this.referencia = referencia;
        this.talla = talla;
        this.color = color;

    }

    // Setters
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.color = color;}

    // Getters
    public String getNombre(){return this.nombre;}
    public String getReferencia(){return this.referencia;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}

}
