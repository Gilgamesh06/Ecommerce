package com.ecommerce.inventario.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ProductoResponseDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String subtipo;

    @Positive
    private Double precioVenta;

    // Constructor
    public ProductoResponseDTO(){}

    public ProductoResponseDTO(String nombre, String subtipo, Double precioVenta){
        this.nombre = nombre;
        this.subtipo = subtipo;
        this.precioVenta = precioVenta;
    }

    // Getters
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setSubtipo(String subtipo){this.subtipo = subtipo;}
    public void setPrecioVenta(Double precioVenta){this.precioVenta = precioVenta;}

    // Setters
    public String getNombre(){return this.nombre;}
    public String getSubtipo(){return this.subtipo;}
    public Double getPrecioVenta(){return this.precioVenta;}

}
