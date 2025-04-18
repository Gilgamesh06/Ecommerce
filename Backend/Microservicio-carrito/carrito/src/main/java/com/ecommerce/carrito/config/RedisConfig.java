package com.ecommerce.carrito.config;

import com.ecommerce.carrito.model.Producto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Producto> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Producto> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Configurar la serialización
        template.setKeySerializer(new StringRedisSerializer()); // Serializa las claves como String
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // Serializa los valores como JSON
        template.setHashKeySerializer(new StringRedisSerializer()); // Serializa las claves del hash como String
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer()); // Serializa los valores del hash como JSON

        return template;
    }

    @Bean
    public CommandLineRunner verifyRedis(RedisTemplate<String, String> redisTemplate) {
        return args -> {
            try {
                redisTemplate.opsForValue().get("test");
                System.out.println("✅ Redis OK");
            } catch (Exception e) {
                System.err.println("⚠️ Redis no está disponible: " + e.getMessage());
            }
        };
    }

}
