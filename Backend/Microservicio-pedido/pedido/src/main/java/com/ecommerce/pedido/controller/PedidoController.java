package com.ecommerce.pedido.controller;

import com.ecommerce.pedido.model.dto.pedido.PedidoResponseCompleteDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseSimpleDTO;
import com.ecommerce.pedido.service.impl.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pedido")
public class PedidoController {

    private  final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping("/listar-pedidos")
    public ResponseEntity<List<PedidoResponseSimpleDTO>> getAllPedidos(){
        List<PedidoResponseSimpleDTO> pedidos = this.pedidoService.getAllPedidos();
        if(pedidos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pedidos,HttpStatus.OK);
    }

    @GetMapping("/listar/{referencia}")
    public ResponseEntity<PedidoResponseCompleteDTO> getPedido(@PathVariable String referencia){
        Optional<PedidoResponseCompleteDTO> pedidoResponseCompleteDTO = this.pedidoService.getPedidoComplete(referencia);
        return pedidoResponseCompleteDTO.map(responseCompleteDTO -> new ResponseEntity<>(responseCompleteDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/listar-completo")
    public ResponseEntity<List<PedidoResponseCompleteDTO>> getAllPedidosComplete(){
        List<PedidoResponseCompleteDTO> pedidoResponseCompleteDTOS = this.pedidoService.getAllPedidoComplete();
        if(pedidoResponseCompleteDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pedidoResponseCompleteDTOS,HttpStatus.OK);
    }
}
