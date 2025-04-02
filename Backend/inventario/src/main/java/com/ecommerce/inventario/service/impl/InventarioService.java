package com.ecommerce.inventario.service.impl;

import com.ecommerce.inventario.model.dto.InventarioAddDTO;
import com.ecommerce.inventario.model.dto.InventarioResponseDTO;
import com.ecommerce.inventario.model.dto.ProductoInfoDTO;
import com.ecommerce.inventario.model.dto.ProductoSearchDTO;
import com.ecommerce.inventario.model.entity.Inventario;
import com.ecommerce.inventario.model.entity.Producto;
import com.ecommerce.inventario.repository.InventarioRepository;
import com.ecommerce.inventario.service.interfaces.CrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService implements CrudInterface<Inventario,InventarioResponseDTO, ProductoSearchDTO, InventarioAddDTO> {

    private final InventarioRepository inventarioRepository;
    private final ProductoService productoService;

    public InventarioService(InventarioRepository inventarioRepository, ProductoService productoService){
        this.inventarioRepository = inventarioRepository;
        this.productoService = productoService;
    }

    private InventarioResponseDTO convetiddorInventarioResponseDTO(Inventario inventario){
        InventarioResponseDTO inventarioResponseDTO = new InventarioResponseDTO();
        ProductoInfoDTO productoInfoDTO = new ProductoInfoDTO();
        Producto producto = inventario.getProducto();
        productoInfoDTO.setReferencia(producto.getReferencia());
        productoInfoDTO.setNombre(producto.getNombre());
        productoInfoDTO.setTalla(producto.getTalla());
        productoInfoDTO.setSubtipo(producto.getSubtipo());
        productoInfoDTO.setColor(producto.getColor());
        productoInfoDTO.setTarget(producto.getTarget());
        productoInfoDTO.setPrecioUnid(producto.getPrecioUnid());
        productoInfoDTO.setPrecioVenta(producto.getPrecioVenta());
        inventarioResponseDTO.setCantidad(inventario.getCantidad());
        inventarioResponseDTO.setProductoInfoDTO(productoInfoDTO);
        return inventarioResponseDTO;
    }

    public Optional<InventarioResponseDTO> findByAtributes(ProductoSearchDTO productoSearchDTO){
        Optional<Producto> productoOpt = Optional.ofNullable(this.productoService.searchProduct(productoSearchDTO));
        Optional<InventarioResponseDTO> inventarioResponseOpt = Optional.empty();
        if(productoOpt.isPresent()){
           Optional<Inventario> inventario =  this.inventarioRepository.findByProductoId(productoOpt.get().getId());
           InventarioResponseDTO inventarioResponseDTO = this.convetiddorInventarioResponseDTO(inventario.get());
           return inventarioResponseOpt.of(inventarioResponseDTO);
        }
        return inventarioResponseOpt;
    }

    public List<InventarioResponseDTO> findAll(){
        List<Inventario> inventarios = this.inventarioRepository.findAll();
        return inventarios.stream()
                .map(this::convetiddorInventarioResponseDTO)
                .toList();
    }

    public InventarioResponseDTO addElement(InventarioAddDTO inventarioAddDTO){
        Producto producto = this.productoService.addProduct(inventarioAddDTO.getProductoAddDTO());
        Inventario inventario = new Inventario();
        inventario.setCantidad(inventarioAddDTO.getCantidad());
        inventario.setProducto(producto);
        this.inventarioRepository.save(inventario);
        return convetiddorInventarioResponseDTO(inventario);
    }

}
