package com.ecommerce.carrito.model.dto.venta;

import com.ecommerce.carrito.model.dto.detalleventa.DetalleVentaResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public class VentaResponseDTO {

    @NotBlank
    private String referencia;

    private String documentoCliente;

    @NotNull
    private LocalDateTime fecha;

    @Positive
    private Double valorVenta;

    @NotBlank
    private String estado;

    @NotNull
    private List<DetalleVentaResponseDTO> detalleVenta;

    // Constructor
    public VentaResponseDTO(){}
    public VentaResponseDTO(String referencia, String documentoCliente, String estado, LocalDateTime fecha, Double valorVenta, List<DetalleVentaResponseDTO> detalleVenta){
        this.referencia = referencia;
        this.documentoCliente = documentoCliente;
        this.estado = estado;
        this.fecha = fecha;
        this.valorVenta = valorVenta;
        this.detalleVenta = detalleVenta;
    }

    // Setters
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setDocumentoCliente(String documentoCliente){this.documentoCliente = documentoCliente;}
    public void setEstado(String estado){this.estado = estado;}
    public void setFecha(LocalDateTime fecha){this.fecha = fecha;}
    public void setValorVenta(Double valorVenta){this.valorVenta = valorVenta;}
    public void setDetalleVenta(List<DetalleVentaResponseDTO> detalleVenta){this.detalleVenta = detalleVenta;}
    // Getters
    public String getReferencia(){return this.referencia;}
    public String getDocumentoCliente(){return this.documentoCliente;}
    public String getEstado(){return this.estado;}
    public LocalDateTime getFecha(){return this.fecha;}
    public Double getValorVenta(){return this.valorVenta;}
    public List<DetalleVentaResponseDTO> getDetalleVenta(){return this.detalleVenta;}

}
