package com.ecommerce.pedido.service.impl;

import com.ecommerce.pedido.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoAddDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseCompleteDTO;
import com.ecommerce.pedido.model.dto.pedido.PedidoResponseSimpleDTO;
import com.ecommerce.pedido.model.entity.Pedido;
import com.ecommerce.pedido.repository.PedidoRepository;
import com.ecommerce.pedido.service.connect.VentaClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final VentaClient ventaClient;

    public PedidoService(PedidoRepository pedidoRepository, VentaClient ventaClient){
        this.pedidoRepository = pedidoRepository;
        this.ventaClient = ventaClient;
    }

    public PedidoResponseSimpleDTO convertPedidoResponseSimpleDTO(Pedido pedido){
        PedidoResponseSimpleDTO pedidoResponseSimpleDTO = new PedidoResponseSimpleDTO();
        pedidoResponseSimpleDTO.setReferencia(pedido.getReferenciaVenta());
        pedidoResponseSimpleDTO.setEstado(pedido.getEstado());
        pedidoResponseSimpleDTO.setFechaRealizacion(pedido.getFechaRealizacion());
        pedidoResponseSimpleDTO.setPrecioTotal(pedido.getValorTotal());
        if(pedido.getFechaEntrega() != null){
            pedidoResponseSimpleDTO.setFechaEntrega(pedido.getFechaEntrega());
        }
        return pedidoResponseSimpleDTO;
    }

    public PedidoResponseCompleteDTO converPedidoResponseCompleteDTO(Pedido pedido, List<DetalleVentaResponseDTO> detalleVentaResponseDTOS){
        PedidoResponseCompleteDTO pedidoResponseCompleteDTO = new PedidoResponseCompleteDTO();
        pedidoResponseCompleteDTO.setReferencia(pedido.getReferenciaVenta());
        pedidoResponseCompleteDTO.setEstado(pedido.getEstado());
        pedidoResponseCompleteDTO.setFechaRealizacion(pedido.getFechaRealizacion());
        pedidoResponseCompleteDTO.setPrecioTotal(pedido.getValorTotal());
        pedidoResponseCompleteDTO.setDetallesPedido(detalleVentaResponseDTOS);
        if(pedido.getFechaEntrega() != null){
            pedidoResponseCompleteDTO.setFechaEntrega(pedido.getFechaEntrega());
        }
        return pedidoResponseCompleteDTO;
    }

    private Pedido crearPedido(PedidoAddDTO pedidoAddDTO){
        // Crea el pedido utilizando la referencia de la venta y el precio total
        Pedido pedido = new Pedido();
        pedido.setReferenciaVenta(pedidoAddDTO.getReferencia());
        pedido.setFechaRealizacion(LocalDateTime.now());
        pedido.setEstado("EN PREPARACION");
        pedido.setValorTotal(pedidoAddDTO.getPrecioTotal());
        return pedido;
    }

    private List<Pedido> getAll(){
        return this.pedidoRepository.findAll();
    }

    public List<PedidoResponseSimpleDTO> getAllPedidos(){
        List<Pedido> pedidos = getAll();
        return pedidos.stream()
                .map(this::convertPedidoResponseSimpleDTO)
                .toList();
    }

    private Pedido guardarPedido(PedidoAddDTO pedidoAddDTO){
        // utiliza el metodo crearPedido para crear el pedido y lo almacena
       return this.pedidoRepository.save(crearPedido(pedidoAddDTO));
    }


    public PedidoResponseSimpleDTO addPedido(PedidoAddDTO pedidoAddDTO){
            // Obtiene un Optional pedido por medio de la referencia de la venta
            Optional<Pedido> pedidoOpt = getPedido(pedidoAddDTO.getReferencia());
            if((pedidoOpt.isEmpty())){
                // Ingresa si el el Optional no contiene un objeto de tipo Pedido
                // Crea el pedido usando el metodo guardarPedido
                Pedido pedido = guardarPedido(pedidoAddDTO);
                // Conviete el pedido en un dto de respuesta y lo retorna
                return convertPedidoResponseSimpleDTO(pedido);
            }
            // Conviete el pedido en un dto de respuesta y lo retorna
            return convertPedidoResponseSimpleDTO(pedidoOpt.get());
    }

    public Optional<Pedido> getPedido(String referencia){
        // retorna un Optional Pedido on la referecia suministrada si no encuentra devuelve
        // un Optional empty
        return  this.pedidoRepository.findByReferenciaVenta(referencia);
    }

    public Optional<PedidoResponseCompleteDTO> getPedidoComplete(String referencia){
        // Obtiene un Optional Pedio por medio de la referencia de la venta
        Optional<Pedido> pedidoOpt = getPedido(referencia);
        if(pedidoOpt.isPresent()){
            // Ingresa si el Optional tiene un Objeto de tipo pedido
            // LLama al metodo del microservicio Venta por medio de HTTP para obtener los detalle de la venta
           List<DetalleVentaResponseDTO> detalleVentaResponseDTOS = this.ventaClient.getDetallePedido(referencia);
           if(!detalleVentaResponseDTOS.isEmpty()){
               // Ingresa si la lista no es vacia
               // Crea un PedioResponseCompleteDTO a partir del pedido y su lista de detalleVentaResponseDTO
               PedidoResponseCompleteDTO pedidoResponseCompleteDTO = converPedidoResponseCompleteDTO(pedidoOpt.get(),
                       detalleVentaResponseDTOS);
               // La encapsula en un Optional y la Retorna
               return Optional.of(pedidoResponseCompleteDTO);
           }
        }
        return Optional.empty();
    }

    private List<String> getAllReference(List<Pedido> pedidos){
        return pedidos.stream()
                .map(Pedido::getReferenciaVenta)
                .toList();
    }

    public List<PedidoResponseCompleteDTO> getAllPedidoComplete(){
        List<Pedido> pedidos = getAll();
        List<String> referencias = getAllReference(pedidos);
        List<PedidoResponseCompleteDTO> pedidoResponseCompleteDTOS = new ArrayList<>();
        List<List<DetalleVentaResponseDTO>> listDetalleVenta = this.ventaClient.getAllDetallePedido(referencias);
        if(listDetalleVenta.size() == referencias.size()){
            for(int i = 0; i < referencias.size(); i++){
                List<DetalleVentaResponseDTO> detalleVenta = listDetalleVenta.get(i);
                PedidoResponseCompleteDTO pedidoResponseCompleteDTO = converPedidoResponseCompleteDTO(pedidos.get(i),detalleVenta);
                pedidoResponseCompleteDTOS.add(pedidoResponseCompleteDTO);
            }
        }
        return pedidoResponseCompleteDTOS;
    }
}
