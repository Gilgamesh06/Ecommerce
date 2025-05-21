package com.ecommerce.inventario.model.dto.catalogo;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.List;

public class ProductoAgrupadoDTO {

    @NotBlank
    private String referencia;

    @NotBlank
    private String nombre;

    @NotBlank
    private Long id;

    @NotBlank
    private String talla;

    @NotBlank
    private String color;

    @NotBlank
    private Double precio;
    @NotNull
    private Set<String> tallas;

    @NotNull
    private Set<String> colores;

    private List<VarianteDTO> variantes;

    // Constructores

    public ProductoAgrupadoDTO(){}
    public ProductoAgrupadoDTO(String referencia, String nombre, Long id,String talla
            ,String color,Double precio, Set<String> tallas
            , Set<String> colores, List<VarianteDTO> variantes){
        this.referencia = referencia;
        this.nombre = nombre;
        this.id = id;
        this.talla = talla;
        this.color = color;
        this.precio = precio;
        this.tallas = tallas;
        this.colores = colores;
        this.variantes = variantes;
    }

    // Setter
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setId(Long id){this.id = id;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.color = color;}
    public void setPrecio(Double precio){this.precio = precio;}
    public void setTallas(Set<String> tallas){this.tallas = tallas;}
    public void setColores(Set<String> colores){this.colores = colores;}
    public void setVariantes(List<VarianteDTO> variantes){this.variantes = variantes;}

    // Getters
    public String getReferencia(){return this.referencia;}
    public String getNombre(){return this.nombre;}
    public Long getId(){return this.id;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
    public Double getPrecio(){return this.precio;}
    public Set<String> getTallas(){return this.tallas;}
    public Set<String> getColores(){return this.colores;}
    public List<VarianteDTO> getVariantes(){return this.variantes;}

}
