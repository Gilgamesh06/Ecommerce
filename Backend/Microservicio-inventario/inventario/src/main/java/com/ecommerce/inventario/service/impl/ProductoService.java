package com.ecommerce.inventario.service.impl;

import com.ecommerce.inventario.model.dto.producto.ProductoAddDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoResponseDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoSearchDTO;
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

    public Optional<Producto> searchProduct(ProductoSearchDTO productoSearchDTO){
        String referencia = productoSearchDTO.getReferencia();
        String talla = productoSearchDTO.getTalla();
        String color = productoSearchDTO.getColor();
        return this.productoRepository.findByReferenciaAndTallaAndColor(referencia,talla,color);
    }

    @Override
    public Optional<ProductoResponseDTO> findByAtributes(ProductoSearchDTO productoSearchDTO) {
        // Obtiene un Optional Producto a partir del dto productoSearchDTO
        Optional<Producto> productoOpt = searchProduct(productoSearchDTO);
        if(productoOpt.isPresent()){
            // Ingresa si Producto esta contenido en Optional
            // Lo comvierte en un ProductoResponse
            ProductoResponseDTO productoResponseDTO = convertProductoResponseDTO(productoOpt.get());
            // Retorna un Optiona ProductoResponseDTO
            return Optional.of(productoResponseDTO);
        }
        return Optional.empty();
    }

    @Override
    public List<ProductoResponseDTO> findAll() {
        List<Producto> productos = this.productoRepository.findAll();
        return productos.stream()
                .map(this::convertProductoResponseDTO)
                .toList();
    }

    @Transactional
    public Producto addProduct(ProductoAddDTO productoAddDTO){
        // Se crea los detalle producto a partir del dto ProductoAddDTO
        DetalleProducto detalleProducto = this.detalleProductoService.addDetailProduct(productoAddDTO.getDetalleProductoAddDTO());
        // Convierte un productoAddDTO en un Objecto de tipo Producto
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
        // Guarda el producto en la base de datos
        return this.productoRepository.save(producto);
    }

    @Override
    public ProductoResponseDTO addElement(ProductoAddDTO productoAddDTO) {
        // LLama al metodo que me guarda el producto y lo obtiene
        Producto producto = addProduct(productoAddDTO);
        // Conviete el Producto en un ProductoResponseDTO
        return convertProductoResponseDTO(producto);
    }


}
