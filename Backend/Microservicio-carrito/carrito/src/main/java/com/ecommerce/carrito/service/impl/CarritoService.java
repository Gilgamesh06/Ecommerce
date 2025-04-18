package com.ecommerce.carrito.service.impl;

import com.ecommerce.carrito.model.Producto;
import com.ecommerce.carrito.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.carrito.model.dto.venta.VentaResponseDTO;
import com.ecommerce.carrito.service.connect.VentaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CarritoService {

    private static final String CARRITO_KEY_PREFIX = "carrito:";

    @Autowired
    private RedisTemplate<String, Producto> redisTemplate;

    private HashOperations<String,String, Producto> hashOperations;

    private VentaClient ventaClient;

    @Autowired
    public CarritoService(RedisTemplate<String,Producto> redisTemplate, VentaClient ventaClient){
        this.redisTemplate = redisTemplate;
        this.hashOperations =redisTemplate.opsForHash();
        this.ventaClient = ventaClient;
    }

    public void addProduct(String sessionId, String productId, Producto producto){
        if(this.hashOperations.get(CARRITO_KEY_PREFIX+sessionId,productId) == null){
            this.hashOperations.put(CARRITO_KEY_PREFIX+sessionId,productId,producto);
        }else{
            if(producto.getCantidad() > 0){
                updateCantidad(sessionId,productId,producto.getCantidad());
            }
        }
    }

    public void deleteProduct(String sessionId, String productoId){
        this.hashOperations.delete(CARRITO_KEY_PREFIX + sessionId, productoId);
    }

    public void updateCantidad(String sessionId, String productoId, int nuevaCantidad) {
        Producto producto = this.hashOperations.get(CARRITO_KEY_PREFIX + sessionId, productoId);
        if (producto != null) {
            if(nuevaCantidad > 0){
                producto.setCantidad(nuevaCantidad);
                this.hashOperations.put(CARRITO_KEY_PREFIX + sessionId, productoId, producto);
            }
            else{
                deleteProduct(sessionId,productoId);
            }
        }
    }

    public void decreaseCantidad(String sessionId, String productoId){
        Producto producto = this.hashOperations.get(CARRITO_KEY_PREFIX + sessionId, productoId);
        if (producto != null) {
            producto.setCantidad(producto.getCantidad()-1);
            if(producto.getCantidad() == 0){
                deleteProduct(sessionId,productoId);
            }
            this.hashOperations.put(CARRITO_KEY_PREFIX + sessionId, productoId, producto);
        }
    }

    public void increaseCantidad(String sessionId, String productoId){
        Producto producto = this.hashOperations.get(CARRITO_KEY_PREFIX + sessionId, productoId);
        if (producto != null) {
            producto.setCantidad(producto.getCantidad()+1);
            this.hashOperations.put(CARRITO_KEY_PREFIX + sessionId, productoId, producto);
        }
    }

    public Map<String, Producto> getCarrito(String sessionId) {
        return hashOperations.entries(CARRITO_KEY_PREFIX + sessionId);
    }

    private CarritoResponseDTO converCarritoResponseDTO(String productId , Producto producto ){
        CarritoResponseDTO carritoResponseDTO = new CarritoResponseDTO();
        Long id = Long.parseLong(productId);
        carritoResponseDTO.setCantidad(producto.getCantidad());
        carritoResponseDTO.setProductoId(id);
        return carritoResponseDTO;
    }

    private void clearCarrito(String sessionId) {
        this.redisTemplate.delete(CARRITO_KEY_PREFIX + sessionId);
    }

    private List<CarritoResponseDTO> generarCarritoCompra(String sessionId){
        Map<String, Producto>  carrito = getCarrito(sessionId);
        List<CarritoResponseDTO> carritoResponseDTOS = new ArrayList<>();
        if(!carrito.isEmpty()){
            Set<String> productIds =  carrito.keySet();
            for(String productoId: productIds){
                Producto producto = carrito.get(productoId);
                CarritoResponseDTO carritoResponseDTO = converCarritoResponseDTO(productoId,producto);
                carritoResponseDTOS.add(carritoResponseDTO);
            }
            return carritoResponseDTOS;
        }
        return carritoResponseDTOS;
    }

    public VentaResponseDTO makeSaleProducts(String sessionId){
        List<CarritoResponseDTO> carrito = generarCarritoCompra(sessionId);
        clearCarrito(sessionId);
        return this.ventaClient.makeSale(carrito);
    }

}
