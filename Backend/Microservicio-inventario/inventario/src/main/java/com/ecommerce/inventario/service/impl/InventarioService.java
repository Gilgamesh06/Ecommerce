package com.ecommerce.inventario.service.impl;

import com.ecommerce.inventario.model.dto.carrito.CarritoResponseDTO;
import com.ecommerce.inventario.model.dto.inventario.InventarioAddDTO;
import com.ecommerce.inventario.model.dto.inventario.InventarioResponseDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoInfoDTO;
import com.ecommerce.inventario.model.dto.producto.ProductoSearchDTO;
import com.ecommerce.inventario.model.entity.Inventario;
import com.ecommerce.inventario.model.entity.Producto;
import com.ecommerce.inventario.repository.InventarioRepository;
import com.ecommerce.inventario.service.interfaces.CrudInterface;
import jakarta.transaction.Transactional;
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

    @Transactional
    public InventarioResponseDTO addElement(InventarioAddDTO inventarioAddDTO){
        Producto producto = this.productoService.addProduct(inventarioAddDTO.getProductoAddDTO());
        Inventario inventario = new Inventario();
        inventario.setCantidad(inventarioAddDTO.getCantidad());
        inventario.setProducto(producto);
        this.inventarioRepository.save(inventario);
        return convetiddorInventarioResponseDTO(inventario);
    }

    private List<Inventario> getInventario(List<Long> ids){
        return this.inventarioRepository.findAllByProductoIdIn(ids);
    }

    public List<InventarioResponseDTO> getProducts(List<Long> ids){
       List<Inventario> inventarios = getInventario(ids);
       return inventarios.stream()
               .map(this::convetiddorInventarioResponseDTO)
               .toList();
    }

    private List<Long> obtenerProductsIds(List<CarritoResponseDTO> carritoResponseDTOS){
        return carritoResponseDTOS.stream()
                .map(CarritoResponseDTO::getProductoId)
                .toList();
    }



    private List<Inventario> updateCantidad(List<CarritoResponseDTO> carritoResponseDTOS){
        List<Long> productIds = obtenerProductsIds(carritoResponseDTOS);
        List<Inventario> inventarios = getInventario(productIds);
        for( int i = 0; i< inventarios.size(); i++){
            int cantidad = inventarios.get(i).getCantidad() - carritoResponseDTOS.get(i).getCantidad();
            inventarios.get(i).setCantidad(cantidad);
        }
        return inventarios;
    }

    @Transactional
    public void updateInventario(List<CarritoResponseDTO> carritoResponseDTOS){
        List<Inventario> inventarios = updateCantidad(carritoResponseDTOS);
        for (Inventario inventario : inventarios) {
            this.inventarioRepository.save(inventario);
        }
    }


}
