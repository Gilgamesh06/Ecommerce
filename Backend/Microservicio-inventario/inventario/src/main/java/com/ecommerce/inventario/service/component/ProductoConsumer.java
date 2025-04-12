package com.ecommerce.inventario.service.component;

import com.ecommerce.inventario.config.RabbitConfig;
import com.ecommerce.inventario.exception.ProductoNoEncontradoException;
import com.ecommerce.inventario.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.inventario.service.impl.InventarioService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class ProductoConsumer {

    private final InventarioService inventarioService;
    private static final Logger logger = LoggerFactory.getLogger(ProductoConsumer.class);

    public ProductoConsumer(InventarioService inventarioService){
        this.inventarioService = inventarioService;
    }

    // Metodo para actualizar cantidad de productos al realizar una venta
    @RabbitListener(queues = RabbitConfig.UPDATE_QUEUE_NAME)
    public void actualizarLibro(List<CarritoResponseDTO> carritoResponseDTOS){
        try{
            this.inventarioService.updateInventario(carritoResponseDTOS);
            logger.info("Cantidad de productos actualizada");
        }catch (ProductoNoEncontradoException ex){
            // Evitar reintentos re-lanzando una excepción que no reencola el mensaje
            throw new AmqpRejectAndDontRequeueException(ex.getMessage(), ex);
        }
    }
}
