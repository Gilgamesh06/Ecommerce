package com.ecommerce.pedido.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "referencia_venta", unique = true, nullable = false)
    private String referenciaVenta;

    private String estado;

    @Column(name = "fecha_realizacion", nullable = false)
    private LocalDateTime fechaRealizacion;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "valor_total")
    private Double valorTotal;

    // Contructores
    public Pedido(){}
    public Pedido(String referenciaVenta, String estado, LocalDateTime fechaRealizacion,
                  LocalDateTime fechaEntrega, Double valorTotal){
        this.referenciaVenta = referenciaVenta;
        this.estado = estado;
        this.fechaRealizacion = fechaRealizacion;
        this.fechaEntrega = fechaEntrega;
        this.valorTotal = valorTotal;
    }

    // Setters
    public void setReferenciaVenta(String referenciaVenta){this.referenciaVenta = referenciaVenta;}
    public void setEstado(String estado){this.estado = estado;}
    public void setFechaRealizacion(LocalDateTime fechaRealizacion){this.fechaRealizacion = fechaRealizacion;}
    public void setFechaEntrega(LocalDateTime fechaEntrega){this.fechaEntrega = fechaEntrega;}
    public void setValorTotal(Double valorTotal){this.valorTotal = valorTotal;}

    // Getters
    public Long getId(){return this.id;}
    public String getReferenciaVenta(){return this.referenciaVenta;}
    public String getEstado(){return this.estado;}
    public LocalDateTime getFechaRealizacion(){return this.fechaRealizacion;}
    public LocalDateTime getFechaEntrega(){return this.fechaEntrega;}
    public Double getValorTotal(){return this.valorTotal;}
}
