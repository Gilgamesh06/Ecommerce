package com.ecommerce.venta.model.dto.pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class PedidoAddDTO {

    @NotBlank
    private String referencia;

    @Positive
    private Double precioTotal;

    // Constructores
    public PedidoAddDTO(){}
    public PedidoAddDTO(String referencia, Double precioTotal){
        this.referencia = referencia;
        this.precioTotal = precioTotal;
    }

    // Setters
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setPrecioTotal(Double precioTotal){this.precioTotal = precioTotal;}

    // Getters
    public String getReferencia(){return this.referencia;}
    public Double getPrecioTotal(){return this.precioTotal;}
}
