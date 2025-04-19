package com.ecommerce.inventario.model.dto.detalleproducto;

import jakarta.validation.constraints.NotBlank;

public class DetalleProductoResponseDTO {
    @NotBlank
    private String descripcion;

    @NotBlank
    private String composicion;

    @NotBlank
    private String pais;

    // Constructores
    public DetalleProductoResponseDTO(){}
    public DetalleProductoResponseDTO(String descripcion, String composicion, String pais){
        this.descripcion = descripcion;
        this.composicion = composicion;
        this.pais = pais;
    }

    // Setters
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}
    public void setComposicion(String composicion){this.composicion = composicion;}
    public void setPais(String pais){this.pais = pais;}

    // Getters
    public String getDescripcion(){return this.descripcion;}
    public String getComposicion(){return this.composicion;}
    public String getPais(){return this.pais;}
}
