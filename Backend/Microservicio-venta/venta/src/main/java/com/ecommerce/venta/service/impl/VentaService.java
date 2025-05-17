package com.ecommerce.venta.service.impl;

import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaAddDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaResponseDTO;
import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.venta.model.dto.pedido.PedidoAddDTO;
import com.ecommerce.venta.model.dto.venta.VentaResponseDTO;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.model.entity.Venta;
import com.ecommerce.venta.repository.VentaRepository;
import com.ecommerce.venta.service.component.VentaProducer;
import com.ecommerce.venta.service.component.ValidationProduct;
import com.ecommerce.venta.service.connect.InventarioClient;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final InventarioClient inventarioClient;
    private final ValidationProduct validationProduct;
    private final ProductoService productoService;
    private final DetalleVentaService detalleVentaService;
    private final VentaProducer ventaProducer;

    public VentaService(VentaRepository ventaRepository, InventarioClient inventarioClient,
                        ValidationProduct validationProduct, ProductoService productoService,
                        DetalleVentaService detalleVentaService, VentaProducer ventaProducer){
        this.ventaRepository = ventaRepository;
        this.inventarioClient = inventarioClient;
        this.validationProduct = validationProduct;
        this.productoService = productoService;
        this.detalleVentaService = detalleVentaService;
        this.ventaProducer = ventaProducer;
    }


    private VentaResponseDTO converVentaResponseDTO(Venta venta, List<DetalleVentaResponseDTO> detalleVenta){
        // Convierte la venta en Una DTO VentaResponseDTO
        VentaResponseDTO ventaResponseDTO = new VentaResponseDTO();
        ventaResponseDTO.setReferencia(venta.getReferencia());
        ventaResponseDTO.setDocumentoCliente(venta.getDocumentoCliente());
        ventaResponseDTO.setFecha(venta.getFecha());
        ventaResponseDTO.setValorVenta(venta.getValorVenta());
        ventaResponseDTO.setEstado(venta.getEstado());
        ventaResponseDTO.setDetalleVenta(detalleVenta);
        return ventaResponseDTO;
    }

    private List<Long> obtenerIds(List<CarritoResponseDTO> carritoResponseDTO){
        return carritoResponseDTO.stream()
                .map(CarritoResponseDTO::getProductoId)
                .toList();

    }

    private List<InventarioResponseDTO> obtenerPorductosInventario(List<Long> productIds){
        return this.inventarioClient.getProducts(productIds);
    }

    @Transactional
    private Venta crearVenta(){
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        return venta;
    }
    private List<DetalleVentaAddDTO> crearDetalleVenta(Venta venta, List<CarritoResponseDTO> carritoResponseDTOs,
                                                       List<Producto> productos){

        List<DetalleVentaAddDTO> detalleVentaAddDTOS = new ArrayList<>();
        for(int i = 0; i< productos.size() ; i++ ){
            detalleVentaAddDTOS.add(this.detalleVentaService.convertDetalleVentaDTO(venta,
                    productos.get(i),
                    carritoResponseDTOs.get(i).getCantidad()));
        }
        return detalleVentaAddDTOS;
    }


    private List<DetalleVentaResponseDTO> guardarDetalleVenta(List<DetalleVentaAddDTO> detalleVentaAddDTOS){
        return this.detalleVentaService.addDetalleVenta(detalleVentaAddDTOS);
    }

    private Double calcularValorVenta(List<DetalleVentaResponseDTO> detalleVentaResposeDTOS ){
        return  detalleVentaResposeDTOS.stream()
                .mapToDouble(DetalleVentaResponseDTO::getPrecioTotal)
                .sum();
    }

    @Transactional
    private Venta guardarVenta(Venta venta, Double valorVenta){
        venta.setValorVenta(valorVenta);
        venta.setEstado("Exitosa");
        venta = this.ventaRepository.save(venta);
        venta.setReferencia("REF-"+ venta.getId());
        return this.ventaRepository.save(venta);
    }

    private PedidoAddDTO converPedidoAddDTO(Venta venta){
        PedidoAddDTO pedidoAddDTO = new PedidoAddDTO();
        pedidoAddDTO.setReferencia(venta.getReferencia());
        pedidoAddDTO.setPrecioTotal(venta.getValorVenta());
        return pedidoAddDTO;
    }

    public Optional<VentaResponseDTO> addElement(List<CarritoResponseDTO> carritoResponseDTOs){
            // Obtiene los id de cada DTO CarritoRespondeDTO y los guarda en una lista
            List<Long> productIds = obtenerIds(carritoResponseDTOs);
            // Obtiene una lista de InventarioResponseDTO a paritr de los id de los productos
            List<InventarioResponseDTO> productosInventario = obtenerPorductosInventario(productIds);

            // Crea un Optional que almacena un VentaResponseDTO
            Optional<VentaResponseDTO> ventaResponseOpt = Optional.empty();

            // Si la cantidad de productos es suficiente entra en el if
            if(this.validationProduct.validarProductos(productosInventario,carritoResponseDTOs)){
                // Obtiene una lista de productos a partir de la lista de dtos InventarioRespondeDTO
                List<Producto> productos = this.productoService.retornarProductosVenta(productosInventario);
                // Crea una venta con la fecha actual
                Venta venta = crearVenta();
                // Crea un los detalleVenta a partir de la venta el carritoResponse y porductosInventario
                List<DetalleVentaAddDTO> detalleVentaAddDTOS = crearDetalleVenta(venta,carritoResponseDTOs,productos);
                // guarda los detalleVenta
                List<DetalleVentaResponseDTO> detalleVentaResposeDTOS = guardarDetalleVenta(detalleVentaAddDTOS);
                // Calcula el valor de la venta
                Double valorVenta = calcularValorVenta(detalleVentaResposeDTOS);
                // Guarda la venta en la base de datos
                venta = guardarVenta(venta,valorVenta);
                // Crea el pedido
                PedidoAddDTO pedidoAddDTO = converPedidoAddDTO(venta);
                // Envia el pedido al microservicio Pedido (HTTP Request)
                this.ventaProducer.sendAddPedido(pedidoAddDTO);
                // Envia la cantidad de los productos vendidos para actualizar (RabbitMQ)
                this.ventaProducer.sendUpdateProduct(carritoResponseDTOs);
                // Retornar una VentaResponseDTO
                return Optional.of(converVentaResponseDTO(venta,detalleVentaResposeDTOS));
            }
            return ventaResponseOpt;
    }

    private Optional<Venta> searchVentaWithReferencia(String referencia){
        return  this.ventaRepository.findByReferencia(referencia);
    }

    // Obtener los detalle de una venta
    public List<DetalleVentaResponseDTO> obtenerDetalles(String referencia){
        Optional<Venta> ventaOpt = searchVentaWithReferencia(referencia);
        if(ventaOpt.isPresent()){
            Long id = ventaOpt.get().getId();
            return this.detalleVentaService.getDetalleVenta(id);
        }
        return new ArrayList<>();
    }

    private List<Venta> getAll(){
        return this.ventaRepository.findAll();
    }

    // Obtener todas las ventas
    public List<VentaResponseDTO> getAllPedidos(){
        List<Venta> ventas = getAll();
        List<VentaResponseDTO> ventaResponseDTOS = new ArrayList<>();
        for(Venta venta : ventas){
            List<DetalleVentaResponseDTO> detalleVentaResposeDTOS = obtenerDetalles(venta.getReferencia());
            VentaResponseDTO  ventaResponseDTO = converVentaResponseDTO(venta,detalleVentaResposeDTOS);
            ventaResponseDTOS.add(ventaResponseDTO);
        }
        return  ventaResponseDTOS;
    }

    public List<List<DetalleVentaResponseDTO>> obtenerAllDetalles(List<String> referencia){
        List<List<DetalleVentaResponseDTO>> listDetallesVenta = new ArrayList<>();
        for(String ref: referencia){
            List<DetalleVentaResponseDTO> detalleVentaResposeDTOS = obtenerDetalles(ref);
            listDetallesVenta.add(detalleVentaResposeDTOS);
        }
        return  listDetallesVenta;
    }

}
