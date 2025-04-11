package com.ecommerce.venta.service.impl;

import com.ecommerce.venta.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaAddDTO;
import com.ecommerce.venta.model.dto.detalleventa.DetalleVentaResposeDTO;
import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.venta.model.dto.venta.VentaResponseDTO;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.model.entity.Venta;
import com.ecommerce.venta.repository.VentaRepository;
import com.ecommerce.venta.service.component.ValidationProduct;
import com.ecommerce.venta.service.connect.InventarioClient;
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

    public VentaService(VentaRepository ventaRepository, InventarioClient inventarioClient,
                        ValidationProduct validationProduct, ProductoService productoService,
                        DetalleVentaService detalleVentaService){
        this.ventaRepository = ventaRepository;
        this.inventarioClient = inventarioClient;
        this.validationProduct = validationProduct;
        this.productoService = productoService;
        this.detalleVentaService = detalleVentaService;
    }


    private VentaResponseDTO converVentaResponseDTO(Venta venta, List<DetalleVentaResposeDTO> detalleVenta){
        VentaResponseDTO ventaResponseDTO = new VentaResponseDTO();
        ventaResponseDTO.setReferencia(venta.getReferencia());
        ventaResponseDTO.setDocumentoCliente(venta.getDocumentoCliente());
        ventaResponseDTO.setFecha(venta.getFecha());
        ventaResponseDTO.setValorVenta(venta.getValorVenta());
        ventaResponseDTO.setEstado(venta.getEstado());
        ventaResponseDTO.setDetalleVenta(detalleVenta);
        return ventaResponseDTO;
    }

    public Optional<VentaResponseDTO> addElement(List<CarritoResponseDTO> carritoResponseDTOs){
            List<Long> productIds = carritoResponseDTOs.stream()
                    .map(CarritoResponseDTO::getProductoId)
                    .toList();
            List<InventarioResponseDTO> productosInventario = this.inventarioClient.getProducts(productIds);

            Optional<VentaResponseDTO> ventaResponseOpt = Optional.empty();

            if(this.validationProduct.validarProductos(productosInventario,carritoResponseDTOs)){
                List<Producto> productos = this.productoService.RetornarProductosVenta(productosInventario);
                Venta venta = new Venta();
                venta.setFecha(LocalDateTime.now());
                venta.setEstado("Exitosa");
                List<DetalleVentaAddDTO> detalleVentaAddDTOS = new ArrayList<>();
                this.ventaRepository.save(venta);
                for(int i = 0; i< productos.size() ; i++ ){
                    detalleVentaAddDTOS.add(this.detalleVentaService.convertDetalleVentaDTO(venta,
                            productos.get(i),
                            carritoResponseDTOs.get(i).getCantidad()));
                }
                List<DetalleVentaResposeDTO> detalleVentaResposeDTOS = this.detalleVentaService.addDetalleVenta(detalleVentaAddDTOS);
                Double valorTotal = detalleVentaResposeDTOS.stream()
                        .mapToDouble(DetalleVentaResposeDTO::getPrecioTotal)
                        .sum();
                venta.setValorVenta(valorTotal);
                venta.setReferencia("REF-"+ venta.getId());
                this.ventaRepository.save(venta);
                return Optional.of(converVentaResponseDTO(venta,detalleVentaResposeDTOS));
            }
            return ventaResponseOpt;
    }
}
