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

    @NotNull
    private Set<String> tallas;

    @NotNull
    private Set<String> colores;

    private List<VarianteDTO> variantes;

    // Constructores

    public ProductoAgrupadoDTO(){}
    public ProductoAgrupadoDTO(String referencia, String nombre, Set<String> tallas
            , Set<String> colores, List<VarianteDTO> variantes){
        this.referencia = referencia;
        this.nombre = nombre;
        this.tallas = tallas;
        this.colores = colores;
        this.variantes = variantes;
    }

    // Setter
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setTallas(Set<String> tallas){this.tallas = tallas;}
    public void setColores(Set<String> colores){this.colores = colores;}
    public void setVariantes(List<VarianteDTO> variantes){this.variantes = variantes;}

    // Getters
    public String getReferencia(){return this.referencia;}
    public String getNombre(){return this.nombre;}
    public Set<String> getTallas(){return this.tallas;}
    public Set<String> getColores(){return this.colores;}
    public List<VarianteDTO> getVariantes(){return this.variantes;}

}
