package com.ecommerce.inventario.service.impl;

import com.ecommerce.inventario.model.dto.ProductoAddDTO;
import com.ecommerce.inventario.model.dto.ProductoResponseDTO;
import com.ecommerce.inventario.model.dto.ProductoSearchDTO;
import com.ecommerce.inventario.model.entity.DetalleProducto;
import com.ecommerce.inventario.model.entity.Producto;
import com.ecommerce.inventario.repository.ProductoRepository;
import com.ecommerce.inventario.service.interfaces.CrudInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProductoService implements CrudInterface<Producto,ProductoResponseDTO, ProductoSearchDTO, ProductoAddDTO> {

    private final ProductoRepository productoRepository;
    private final DetalleProductoService detalleProductoService;

    public ProductoService(ProductoRepository productoRepository, DetalleProductoService detalleProductoService){
        this.productoRepository = productoRepository;
        this.detalleProductoService = detalleProductoService;
    }

    private ProductoResponseDTO convertProductoResponseDTO(Producto producto){
        ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO();
        productoResponseDTO.setNombre(producto.getNombre());
        productoResponseDTO.setSubtipo(producto.getSubtipo());
        productoResponseDTO.setPrecioVenta(producto.getPrecioVenta());
        return productoResponseDTO;
    }

    public Producto searchProduct(ProductoSearchDTO productoSearchDTO){
        String referencia = productoSearchDTO.getReferencia();
        String talla = productoSearchDTO.getTalla();
        String color = productoSearchDTO.getColor();
        Optional<Producto> productoOpt = this.productoRepository.findByReferenciaAndTallaAndColor(referencia,talla,color);
        return productoOpt.orElse(null);
    }

    @Override
    public Optional<ProductoResponseDTO> findByAtributes(ProductoSearchDTO productoSearchDTO) {
        Optional<ProductoResponseDTO> responseOpt = Optional.empty();
        if(searchProduct(productoSearchDTO) != null){
            Producto producto = searchProduct(productoSearchDTO);
            ProductoResponseDTO productoResponseDTO = convertProductoResponseDTO(producto);
            return responseOpt.of(productoResponseDTO);
        }
        return responseOpt;
    }

    @Override
    public List<ProductoResponseDTO> findAll() {
        List<Producto> productos = this.productoRepository.findAll();
        return productos.stream()
                .map(this::convertProductoResponseDTO)
                .toList();
    }

    public Producto addProduct(ProductoAddDTO productoAddDTO){
        DetalleProducto detalleProducto = this.detalleProductoService.addDetailProduct(productoAddDTO.getDetalleProductoAddDTO());
        Producto producto = Stream.of(productoAddDTO)
                .map(dto -> {
                    Producto p = new Producto();
                    p.setReferencia(dto.getReferencia());
                    p.setNombre(dto.getNombre());
                    p.setTalla(dto.getTalla());
                    p.setTipo(dto.getTipo());
                    p.setSubtipo(dto.getSubtipo());
                    p.setTarget(dto.getTarget());
                    p.setColor(dto.getColor());
                    p.setPrecioUnid(dto.getPrecioUnid());
                    p.setPrecioVenta(dto.getPrecioVenta());
                    p.setDetalleProducto(detalleProducto);
                    return p;
                })
                .findFirst()
                .orElse(null);
        this.productoRepository.save(producto);
        return producto;
    }

    @Override
    @Transactional
    public ProductoResponseDTO addElement(ProductoAddDTO productoAddDTO) {
        Producto producto = addProduct(productoAddDTO);
        return convertProductoResponseDTO(producto);
    }


}
