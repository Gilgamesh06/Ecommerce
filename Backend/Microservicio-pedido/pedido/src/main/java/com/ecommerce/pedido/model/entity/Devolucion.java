package com.ecommerce.pedido.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "devolucion")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private LocalDateTime fecha;


    // Constructores
    public Devolucion(){}
    public Devolucion(Pedido pedido, LocalDateTime fecha){
        this.pedido = pedido;
        this.fecha = fecha;
    }

    // Setters
    public void setPedido(Pedido pedido){this.pedido = pedido;}
    public void setFecha(LocalDateTime fecha){this.fecha = fecha;}

    // Getters
    public Long getId(){return this.id;}
    public Pedido getPedido(){return this.pedido;}
    public LocalDateTime getFecha(){return this.fecha;}
}
