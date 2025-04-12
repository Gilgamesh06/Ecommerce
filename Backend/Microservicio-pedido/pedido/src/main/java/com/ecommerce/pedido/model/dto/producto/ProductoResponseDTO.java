package com.ecommerce.pedido.model.dto.producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ProductoResponseDTO {
    @NotBlank
    private String nombre;

    @NotBlank
    private String referencia;

    @NotBlank
    private String talla;

    @NotBlank
    private String color;

    @Positive
    private Double precioVenta;

    // Constructores
    public ProductoResponseDTO(){}
    public ProductoResponseDTO(String nombre, String referencia, String talla, String color, Double precioVenta){
        this.nombre = nombre;
        this.referencia = referencia;
        this.talla = talla;
        this.color = color;
        this.precioVenta = precioVenta;

    }

    // Setters
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.color = color;}
    public void setPrecioVenta(Double precioVenta){this.precioVenta = precioVenta;}

    // Getters
    public String getNombre(){return this.nombre;}
    public String getReferencia(){return this.referencia;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
    public @Positive Double getPrecioVenta(){return this.precioVenta;}
}
