package com.ecommerce.pedido.model.dto.pedido;

import com.ecommerce.pedido.model.dto.detalleventa.DetalleVentaResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponseCompleteDTO {

    @NotBlank
    private String referencia;

    @NotBlank
    private String estado;

    @NotNull
    private LocalDateTime fechaRealizacion;

    private LocalDateTime fechaEntrega;

    @Positive
    private Double precioTotal;

    private List<DetalleVentaResponseDTO> detallesPedido;

    // Constructores
    public PedidoResponseCompleteDTO(){}
    public PedidoResponseCompleteDTO(String referencia, String estado, LocalDateTime fechaRealizacion,
                                   LocalDateTime fechaEntrega, Double precioTotal,
                                     List<DetalleVentaResponseDTO> detallesPedido){
        this.referencia = referencia;
        this.estado = estado;
        this.fechaRealizacion = fechaRealizacion;
        this.fechaEntrega = fechaEntrega;
        this.precioTotal = precioTotal;
        this.detallesPedido = detallesPedido;
    }

    // Setters
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setEstado(String estado){this.estado = estado;}
    public void setFechaRealizacion(LocalDateTime fechaRealizacion){this.fechaRealizacion = fechaRealizacion;}
    public void setFechaEntrega(LocalDateTime fechaEntrega){this.fechaEntrega = fechaEntrega;}
    public void setPrecioTotal(Double precioTotal){this.precioTotal = precioTotal;}
    public void setDetallesPedido(List<DetalleVentaResponseDTO> detallesPedido){this.detallesPedido = detallesPedido;}
    // Getters
    public String getReferencia(){return this.referencia;}
    public String getEstado(){return this.estado;}
    public LocalDateTime getFechaRealizacion(){return this.fechaRealizacion;}
    public LocalDateTime getFechaEntrega(){return this.fechaEntrega;}
    public Double getPrecioTotal(){return this.precioTotal;}
    public List<DetalleVentaResponseDTO> getDetallesPedido(){return this.detallesPedido;}

}
