package com.ecommerce.inventario.model.dto.inventario;

import com.ecommerce.inventario.model.dto.producto.ProductoAddDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class InventarioAddDTO {

    @PositiveOrZero
    private Integer cantidad;

    @NotNull
    private ProductoAddDTO productoAddDTO;

    // Constructores
    public InventarioAddDTO(){}
    public InventarioAddDTO(Integer cantidad, ProductoAddDTO productoAddDTO){
        this.cantidad = cantidad;
        this.productoAddDTO = productoAddDTO;
    }

    // Setters
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}
    public void setProductoAddDTO(ProductoAddDTO productoAddDTO){this.productoAddDTO = productoAddDTO;}

    // Getters
    public Integer getCantidad(){return this.cantidad;}
    public ProductoAddDTO getProductoAddDTO(){return this.productoAddDTO;}


}
