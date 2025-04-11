package com.ecommerce.inventario.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductoAddDTO {

    @NotBlank
    private String referencia;

    @NotBlank
    private String nombre;

    @NotBlank
    private String talla;

    @NotBlank
    private String tipo;

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

    @NotNull
    private DetalleProductoAddDTO detalleProductoAddDTO;

    public ProductoAddDTO() {}

    public ProductoAddDTO(String referencia, String nombre,String tipo, String talla, String color, String target, String subtipo, Double precioUnid, Double precioVenta , DetalleProductoAddDTO detalleProductoAddDTO){
        this.referencia = referencia;
        this.nombre = nombre;
        this.tipo = tipo;
        this.talla = talla;
        this.color = color;
        this.target = target;
        this.subtipo = subtipo;
        this.precioUnid = precioUnid;
        this.precioVenta = precioVenta;
        this.detalleProductoAddDTO = detalleProductoAddDTO;
    }

    // Setters
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setTipo(String tipo){this.tipo = tipo;}
    public void setSubtipo(String subtipo){this.subtipo = subtipo;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.color = color;}
    public void setTarget(String target){this.target = target;}
    public void setPrecioUnid(Double precioUnid){this.precioUnid = precioUnid;}
    public void setPrecioVenta(Double precioVenta){this.precioVenta = precioVenta;}
    public void setDetalleProductoAddDTO(DetalleProductoAddDTO detalleProductoAddDTO){this.detalleProductoAddDTO = detalleProductoAddDTO;}
    // Getters
    public String getReferencia(){return this.referencia;}
    public String getNombre(){return nombre;}
    public String getTipo(){return this.tipo;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
    public String getSubtipo(){return this.subtipo;}
    public String getTarget(){return this.target;}
    public Double getPrecioUnid(){return this.precioUnid;}
    public Double getPrecioVenta(){return this.precioVenta;}
    public DetalleProductoAddDTO getDetalleProductoAddDTO(){return this.detalleProductoAddDTO;}

}
