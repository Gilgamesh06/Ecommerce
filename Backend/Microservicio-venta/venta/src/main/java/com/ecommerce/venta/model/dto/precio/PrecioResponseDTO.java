package com.ecommerce.venta.model.dto.precio;

import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PrecioResponseDTO {

    @PositiveOrZero
    private Double precioVenta;

    private LocalDateTime fecha;

    // Constructor
    public PrecioResponseDTO(){}
    public PrecioResponseDTO(Double precioVenta, LocalDateTime fecha){
        this.fecha = fecha;
        this.precioVenta = precioVenta;
    }

    // Setters
    public void setPrecioVenta(Double precioVenta){this.precioVenta = precioVenta;}
    public void setFecha(LocalDateTime fecha){this.fecha = fecha;}

    // Getters
    public Double getPrecioVenta(){return this.precioVenta;}
    public LocalDateTime getFecha(){return this.fecha;}
}
