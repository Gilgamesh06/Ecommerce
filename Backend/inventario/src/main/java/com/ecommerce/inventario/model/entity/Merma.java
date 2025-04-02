package com.ecommerce.inventario.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity(name = "merma")
public class Merma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referencia;

    private LocalDate fecha;

    // Constructores
    public Merma(){}
    public Merma(LocalDate fecha, String referencia){ this.fecha = fecha; this.referencia = referencia;}

    // Setters
    public void setFecha(LocalDate fecha){this.fecha = fecha;}

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    // Getters
    public Long getId(){return this.id;}
    public LocalDate getFecha(){return this.fecha;}
    public String getReferencia(){return this.referencia;}
}
