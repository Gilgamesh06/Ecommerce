package com.ecommerce.venta.service.component;

import com.ecommerce.venta.config.RabbitConfig;
import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProductoProducer {

    private final RabbitTemplate rabbitTemplate;

    public ProductoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendUpdateProduct(List<CarritoResponseDTO> carritoResponseDTOS){
        this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,RabbitConfig.INVENTARIO_ROUTING_KEY,carritoResponseDTOS);
    }
}
