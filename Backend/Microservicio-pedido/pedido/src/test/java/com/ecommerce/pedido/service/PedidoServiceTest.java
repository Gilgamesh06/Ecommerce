package com.ecommerce.pedido.service;

import com.ecommerce.pedido.TestDataProvider;
import com.ecommerce.pedido.model.dto.pedido.PedidoAddDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseSimpleDTO;
import com.ecommerce.pedido.model.entity.Pedido;
import com.ecommerce.pedido.repository.PedidoRepository;
import com.ecommerce.pedido.service.impl.PedidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private final TestDataProvider testDataProvider = new TestDataProvider();
    @Test
    public void createdPedidoTest(){

        // Objeto que recibe el metodo addPedido
        PedidoAddDTO  pedidoAddDTO = this.testDataProvider.getPedidoAddDTO();

        // Busca si ya estiste el pedido en este caso lo estamos creado
        // por lo cual retorna un Optional empty
        when(this.pedidoRepository.findByReferenciaVenta(eq(pedidoAddDTO.getReferencia())))
                .thenReturn(Optional.empty());

        // Retorna el objeto que recibe en este caso un Objeto de tipo Pedido
        when(this.pedidoRepository.save(any(Pedido.class))).thenAnswer(
                inv -> inv.getArgument(0)
        );

        // Metodo a probbar
        PedidoResponseSimpleDTO pedido = this.pedidoService.addPedido(pedidoAddDTO);

        // Validaciones
        assertNotNull(pedido);
        assertEquals(pedidoAddDTO.getReferencia(),pedido.getReferencia());
        assertEquals("EN PREPARACION", pedido.getEstado());
        assertEquals(pedidoAddDTO.getPrecioTotal(),pedido.getPrecioTotal());
    }

    @Test
    public void createdPedidoExistenteTest(){

        // Objeto que recibe el metodo addPedido
        PedidoAddDTO  pedidoAddDTO = this.testDataProvider.getPedidoAddDTO();

        // Objeto que retorna la busqueda por referencia
        Pedido pedido = this.testDataProvider.getPedido();

        // Busca si ya estiste el pedido en este caso lo estamos creado por lo cual retorna un Optional empty
        when(this.pedidoRepository.findByReferenciaVenta(eq(pedidoAddDTO.getReferencia()))).thenReturn(Optional.of(pedido));

        // Metodo a probbar
        PedidoResponseSimpleDTO pedidoResponseDTO = this.pedidoService.addPedido(pedidoAddDTO);

        // Validaciones
        assertNotNull(pedidoResponseDTO);
        assertEquals(pedidoAddDTO.getReferencia(),pedidoResponseDTO.getReferencia());
        assertEquals("EN PREPARACION", pedidoResponseDTO.getEstado());
        assertEquals(pedidoAddDTO.getPrecioTotal(),pedidoResponseDTO.getPrecioTotal());
    }
}
