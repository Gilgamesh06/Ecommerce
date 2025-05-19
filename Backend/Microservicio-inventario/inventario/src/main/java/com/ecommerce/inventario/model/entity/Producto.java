package com.ecommerce.inventario.model.entity;

import jakarta.persistence.*;

@Entity(name="producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String referencia;

    private String nombre;

    @Column(length = 10)
    private String talla;

    @Column(length = 100)
    private String tipo;

    @Column(length = 150)
    private String subtipo;

    private String target;

    private String color;

    @Column(name="precio_unid")
    private Double precioUnid;

    @Column(name="precio_venta")
    private Double precioVenta;

    @OneToOne
    @JoinColumn(name = "detalle_producto_id")
    private DetalleProducto detalleProducto;

    // Constructores
    public Producto(){}
    public Producto(String referencia, String nombre,String tipo, String talla, String color, String target, String subtipo, Double precioUnid, Double precioVenta, DetalleProducto detalleProducto){
        this.referencia = referencia;
        this.nombre = nombre;
        this.tipo = tipo;
        this.talla = talla;
        this.color = color;
        this.target = target;
        this.subtipo = subtipo;
        this.precioUnid = precioUnid;
        this.precioVenta = precioVenta;
        this.detalleProducto = detalleProducto;
    }

    // Setters
    public void setId(Long id){this.id = id;}
    public void setReferencia(String referencia){this.referencia = referencia;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setTipo(String tipo){this.tipo = tipo;}
    public void setSubtipo(String subtipo){this.subtipo = subtipo;}
    public void setTalla(String talla){this.talla = talla;}
    public void setColor(String color){this.color = color;}
    public void setPrecioUnid(Double precioUnid){this.precioUnid = precioUnid;}
    public void setPrecioVenta(Double precioVenta){this.precioVenta = precioVenta;}
    public void setDetalleProducto(DetalleProducto detalleProducto){this.detalleProducto= detalleProducto;}
    public void setTarget(String target){this.target = target;}

    // Getters
    public Long getId(){return this.id;}
    public String getReferencia(){return this.referencia;}
    public String getNombre(){return nombre;}
    public String getTipo(){return this.tipo;}
    public String getTalla(){return this.talla;}
    public String getColor(){return this.color;}
    public String getSubtipo(){return this.subtipo;}
    public Double getPrecioUnid(){return this.precioUnid;}
    public Double getPrecioVenta(){return this.precioVenta;}
    public DetalleProducto getDetalleProducto(){return this.detalleProducto;}
    public String getTarget(){return this.target;}
}
