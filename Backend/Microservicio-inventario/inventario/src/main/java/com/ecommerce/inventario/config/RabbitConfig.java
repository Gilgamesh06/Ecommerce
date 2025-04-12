package com.ecommerce.inventario.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static final String EXCHANGE_NAME = "productoExchange";

    // Para actualizar cantidad de un producto
    public static final String UPDATE_ROUTING_KEY = "updateCantidad.key";
    public static final String UPDATE_QUEUE_NAME = "productoQueueCantidadUpdate";

    // Declarar cola para actualizar cantidad
    @Bean
    public Queue updateQueue() {
        return new Queue(UPDATE_QUEUE_NAME, false);
    }

    // Declarar el exchange DirectExchange
    @Bean
    public DirectExchange productoExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Binding para la cola de actualizar cantidad
    @Bean
    public Binding bindingUpdateQueue(Queue updateQueue, DirectExchange productoExchange) {
        return BindingBuilder.bind(updateQueue).to(productoExchange).with(UPDATE_ROUTING_KEY);
    }

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;
    @Value("${spring.rabbitmq.port}")
    private int rabbitPort;
    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;
    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages(
                "com.ecommerce.inventario.model.dto.carrito",
                "com.ecommerce.venta.model.dto.carrito"
        );
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
