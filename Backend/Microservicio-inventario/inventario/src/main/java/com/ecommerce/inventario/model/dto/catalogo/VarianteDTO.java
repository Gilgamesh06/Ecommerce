package com.ecommerce.inventario.model.dto.catalogo;

import com.ecommerce.inventario.model.dto.detalleproducto.DetalleProductoResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VarianteDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String talla;

    @NotBlank
    private String color;

    @NotNull
    private DetalleProductoResponseDTO detalle;

    // Constructor
    public VarianteDTO(){}
    public VarianteDTO(Long id, String talla, String color,
                       DetalleProductoResponseDTO detalle){
        this.id = id;
        this.talla = talla;
        this.color = color;
        this.detalle = detalle;
    }

    // Setters
    public void setId(Long id){this.id = id;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.talla = talla;}
    public void setDetalle(DetalleProductoResponseDTO detalle){this.detalle = detalle;}

    // Getters
    public Long getId(){return this.id;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
    public DetalleProductoResponseDTO getDetalle(){return this.detalle;}
}
