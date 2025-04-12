package com.ecommerce.inventario.exception;

public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(String message){
        super(message);
    }
}
