package com.ecommerce.venta.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name="venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String referencia;

    @Column(name = "documento_cliente")
    private String documentoCliente;

    private LocalDateTime fecha;

    @Column(name="valor_venta")
    private Double valorVenta;

    private String estado;

    // Constructor
    public Venta(){}
    public Venta(String referencia, String documentoCliente, String estado, LocalDateTime fecha, Double valorVenta){
        this.referencia = referencia;
        this.documentoCliente = documentoCliente;
        this.estado = estado;
        this.fecha = fecha;
        this.valorVenta = valorVenta;
    }

    // Setters
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setDocumentoCliente(String documentoCliente){this.documentoCliente = documentoCliente;}
    public void setEstado(String estado){this.estado = estado;}
    public void setFecha(LocalDateTime fecha){this.fecha = fecha;}
    public void setValorVenta(Double valorVenta){this.valorVenta = valorVenta;}

    // Getters
    public Long getId(){return this.id;}
    public String getReferencia(){return this.referencia;}
    public String getDocumentoCliente(){return this.documentoCliente;}
    public String getEstado(){return this.estado;}
    public LocalDateTime getFecha(){return this.fecha;}
    public Double getValorVenta(){return this.valorVenta;}

}
