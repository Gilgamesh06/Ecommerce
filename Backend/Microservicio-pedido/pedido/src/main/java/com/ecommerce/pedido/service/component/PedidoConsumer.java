package com.ecommerce.pedido.service.component;

import com.ecommerce.pedido.config.RabbitConfig;
import com.ecommerce.pedido.exception.PedidoNoCreadoException;
import com.ecommerce.pedido.model.dto.pedido.PedidoAddDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseSimpleDTO;
import com.ecommerce.pedido.service.impl.PedidoService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PedidoConsumer {

    private final PedidoService pedidoService;
    private static final Logger logger = LoggerFactory.getLogger(PedidoConsumer.class);

    public PedidoConsumer(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    // Metodo para Crear un pedido despues de realizar una venta
    @RabbitListener(queues = RabbitConfig.ADD_QUEUE_NAME)
    public void addPedido(PedidoAddDTO pedidoAddDTO){
        try {
            logger.info("Pedido Creado: "+ pedidoAddDTO.getReferencia());
            this.pedidoService.addPedido(pedidoAddDTO);
        }catch (PedidoNoCreadoException ex){
            // Evitar reintentos re-lanzando una excepción que no reencola el mensaje
            throw new AmqpRejectAndDontRequeueException(ex.getMessage(), ex);
        }
    }
}
