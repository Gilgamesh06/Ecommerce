package com.ecommerce.carrito.controller;

import com.ecommerce.carrito.model.Producto;
import com.ecommerce.carrito.model.dto.venta.VentaResponseDTO;
import com.ecommerce.carrito.service.impl.CarritoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService){
        this.carritoService = carritoService;
    }

    @GetMapping("/iniciar")
    public String startCarrito(HttpServletResponse response){
        // Generar un Sessionid unico
        String sessionId = UUID.randomUUID().toString();

        // Crear una cookie para el sessionId
        Cookie cookie = new Cookie("session_id",sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Inicializar el carrito
        return "Carrito iniciado con sessionId: "+sessionId;
    }

    @PostMapping("/{sessionId}/agregar/{productId}")
    public void addProduct(@PathVariable String sessionId, @PathVariable Long productId,
                                @RequestBody Producto producto){
        this.carritoService.addProduct(sessionId,String.valueOf(productId),producto);
    }

    @DeleteMapping("/{sessionId}/eliminar/{productId}")
    public void deleteProduct(@PathVariable String sessionId, @PathVariable Long productId) {
        this.carritoService.deleteProduct(sessionId,String.valueOf(productId));
    }

    @PutMapping("/{sessionId}/actualizar/{productoId}/{cantidad}")
    public void updateCantidad(@PathVariable String sessionId , @PathVariable Long productoId
            , Integer cantidad){
        this.carritoService.updateCantidad(sessionId,String.valueOf(productoId),cantidad);
    }

    @PutMapping("/{sessionId}/aumentar/{productoId}")
    public void increaseCantidad(@PathVariable String sessionId , @PathVariable Long productoId
            , Integer cantidad){
        this.carritoService.increaseCantidad(sessionId,String.valueOf(productoId));
    }

    @PutMapping("/{sessionId}/disminuir/{productoId}")
    public void decreaseCantidad(@PathVariable String sessionId , @PathVariable Long productoId
            , Integer cantidad){
        this.carritoService.decreaseCantidad(sessionId,String.valueOf(productoId));
    }

    @GetMapping("/{sessionId}/comprar")
    public VentaResponseDTO buyProducts(@PathVariable String sessionId){
        return this.carritoService.makeSaleProducts(sessionId);
    }

    @GetMapping("/{sessionId}")
    public Map<String, Producto> getCarrito(@PathVariable String sessionId) {
        return this.carritoService.getCarrito(sessionId);
    }

}
