package com.ecommerce.pedido.model.dto.pedido;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;


public class PedidoResponseSimpleDTO {

        @NotBlank
        private String referencia;

        @NotBlank
        private String estado;

        @NotNull
        private LocalDateTime fechaRealizacion;

        private LocalDateTime fechaEntrega;

        @Positive
        private Double precioTotal;

        // Constructores
        public PedidoResponseSimpleDTO(){}
        public PedidoResponseSimpleDTO(String referencia, String estado, LocalDateTime fechaRealizacion,
                                       LocalDateTime fechaEntrega, Double precioTotal){
            this.referencia = referencia;
            this.estado = estado;
            this.fechaRealizacion = fechaRealizacion;
            this.fechaEntrega = fechaEntrega;
            this.precioTotal = precioTotal;
        }

        // Setters
        public void setReferencia(String referencia){this.referencia = referencia;}
        public void setEstado(String estado){this.estado = estado;}
        public void setFechaRealizacion(LocalDateTime fechaRealizacion){this.fechaRealizacion = fechaRealizacion;}
        public void setFechaEntrega(LocalDateTime fechaEntrega){this.fechaEntrega = fechaEntrega;}
        public void setPrecioTotal(Double precioTotal){this.precioTotal = precioTotal;}

        // Getters
        public String getReferencia(){return this.referencia;}
        public String getEstado(){return this.estado;}
        public LocalDateTime getFechaRealizacion(){return this.fechaRealizacion;}
        public LocalDateTime getFechaEntrega(){return this.fechaEntrega;}
        public Double getPrecioTotal(){return this.precioTotal;}

}
