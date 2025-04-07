package com.ecommerce.venta.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "rembolso")
public class Rembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    private LocalDateTime fecha;

    // Constructor
    public Rembolso(){}
    public Rembolso(Venta venta, LocalDateTime fecha){
        this.venta = venta;
        this.fecha = fecha;
    }

    // Setter
    public void setVenta(Venta venta){this.venta = venta;}
    public void setFecha(LocalDateTime fecha){this.fecha = fecha;}

    // Getters
    public Long getId(){return this.id;}
    public Venta getVenta(){return this.venta;}
    public LocalDateTime getFecha(){return this.fecha;}
}
