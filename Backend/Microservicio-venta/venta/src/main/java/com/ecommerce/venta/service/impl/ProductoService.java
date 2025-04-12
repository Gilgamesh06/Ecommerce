package com.ecommerce.venta.service.impl;

import com.ecommerce.venta.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.venta.model.dto.precio.PrecioAddDTO;
import com.ecommerce.venta.model.dto.producto.ProductoAddDTO;
import com.ecommerce.venta.model.dto.producto.ProductoInfoDTO;
import com.ecommerce.venta.model.dto.producto.ProductoResponseDTO;
import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.repository.ProductoRepository;
import com.ecommerce.venta.service.interfaces.GetAndSave;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements GetAndSave<Producto, ProductoResponseDTO, ProductoAddDTO> {

    private final ProductoRepository productoRepository;
    private final PrecioService precioService;

    public ProductoService(ProductoRepository productoRepository, PrecioService precioService){
        this.productoRepository = productoRepository;
        this.precioService = precioService;
    }

    public ProductoResponseDTO convertProductoResposeDTO(Producto producto){
        Optional<Precio> precioOpt = this.precioService.getPrecio(producto.getId());
        ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO();
        productoResponseDTO.setNombre(producto.getNombre());
        productoResponseDTO.setReferencia(producto.getReferencia());
        productoResponseDTO.setTalla(producto.getTalla());
        productoResponseDTO.setColor(producto.getColor());
        precioOpt.ifPresent(precio -> productoResponseDTO.setPrecioVenta(precio.getPrecioVenta()));
        return productoResponseDTO;
    }

    private ProductoAddDTO converProductoAddDTO(ProductoInfoDTO productoInfoDTO){
        ProductoAddDTO productoAddDTO = new ProductoAddDTO();
        productoAddDTO.setNombre(productoInfoDTO.getNombre());
        productoAddDTO.setReferencia(productoInfoDTO.getReferencia());
        productoAddDTO.setTalla(productoInfoDTO.getTalla());
        productoAddDTO.setColor(productoInfoDTO.getColor());
        productoAddDTO.setSubtipo(productoInfoDTO.getSubtipo());
        productoAddDTO.setTarget(productoInfoDTO.getTarget());

        return productoAddDTO;
    }

    @Override
    public List<ProductoResponseDTO> findAll() {
        List<Producto> productos = this.productoRepository.findAll();
        return productos.stream()
                .map(this::convertProductoResposeDTO)
                .toList();
    }

    @Transactional
    public Producto addProducto(ProductoAddDTO productoAddDTO){
        Producto producto = new Producto();
        producto.setNombre(productoAddDTO.getNombre());
        producto.setReferencia(productoAddDTO.getReferencia());
        producto.setTalla(productoAddDTO.getTalla());
        producto.setColor(productoAddDTO.getColor());
        producto.setSubtipo(productoAddDTO.getSubtipo());
        producto.setTarget(productoAddDTO.getTarget());
        return this.productoRepository.save(producto);
    }

    public Optional<Producto> searchProducto(String referencia, String talla, String color){
        return this.productoRepository.findByReferenciaAndTallaAndColor(referencia,talla,color);
    }


    /*
    Este metodo me comprueba que los productos del servicio del inventario esten si no estan los agrega
    y retorna una lista de PrecioAddDTO para manejar la logica de historial de precios
     */
    public List<PrecioAddDTO> ComporbarProductosVenta(List<InventarioResponseDTO> inventarios){
       List<PrecioAddDTO> precios = new ArrayList<>();
        for(InventarioResponseDTO inventario: inventarios){
            Optional<Producto> productoOpt = searchProducto(
                    inventario.getProductoInfoDTO().getReferencia(),
                    inventario.getProductoInfoDTO().getTalla(),
                    inventario.getProductoInfoDTO().getColor());
            if(productoOpt.isEmpty()){
                Producto producto = addProducto(converProductoAddDTO(inventario.getProductoInfoDTO()));
                PrecioAddDTO precioAddDTO =this.precioService.converPrecioAddDTO(inventario.getProductoInfoDTO().getPrecioUnid(),
                        inventario.getProductoInfoDTO().getPrecioVenta(),
                        producto);
                precios.add(precioAddDTO);
            }
            else{
                PrecioAddDTO precioAddDTO = this.precioService.converPrecioAddDTO(inventario.getProductoInfoDTO().getPrecioUnid(),
                        inventario.getProductoInfoDTO().getPrecioVenta(),
                        productoOpt.get());
                precios.add(precioAddDTO);
            }
        }
        return precios;
    }

    public List<Producto> RetornarProductosVenta(List<InventarioResponseDTO> productosInventario){
        List<PrecioAddDTO> precioAddDTOS = this.ComporbarProductosVenta(productosInventario);
        List<Precio> precios = precioAddDTOS.stream()
                .map(this.precioService::addPrecio)
                .toList();
        return  precios.stream()
                .map(Precio::getProducto)
                .toList();
    }

    @Override
    public ProductoResponseDTO addElement(ProductoAddDTO addProducto) {
        return convertProductoResposeDTO(addProducto(addProducto));
    }
}
