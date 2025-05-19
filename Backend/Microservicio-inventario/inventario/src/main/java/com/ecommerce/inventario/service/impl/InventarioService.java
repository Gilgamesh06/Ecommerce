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

    private InventarioResponseDTO convertInventarioResponseDTO(Inventario inventario){
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

    public ProductoSearchDTO converProductoSearchDTO(String referencia,
                                                     String talla,String color){
        ProductoSearchDTO productoSearchDTO = new ProductoSearchDTO();
        productoSearchDTO.setReferencia(referencia);
        productoSearchDTO.setTalla(talla);
        productoSearchDTO.setColor(color);
        return productoSearchDTO;
    }

    public Optional<InventarioResponseDTO> findByAtributes(ProductoSearchDTO productoSearchDTO){
        // Busca el producto en la base de datos
        Optional<Producto> productoOpt = this.productoService.searchProduct(productoSearchDTO);
        if(productoOpt.isPresent()){
            // busca el inventario asociado a ese producto
           Optional<Inventario> inventarioOpt =  this.inventarioRepository.findByProductoId(productoOpt.get().getId());
           if(inventarioOpt.isPresent()){
               // si lo encuentra la convierte en un DTO InventarioResponseDTO
               InventarioResponseDTO inventarioResponseDTO = this.convertInventarioResponseDTO(inventarioOpt.get());
               return Optional.of(inventarioResponseDTO);
           }
        }
        return Optional.empty();
    }

    public List<InventarioResponseDTO> findAll(){
        List<Inventario> inventarios = this.inventarioRepository.findAll();
        return inventarios.stream()
                .map(this::convertInventarioResponseDTO)
                .toList();
    }

    @Transactional
    public InventarioResponseDTO addElement(InventarioAddDTO inventarioAddDTO){
        // Verifica que el producto ya no este en el inventario
        ProductoSearchDTO productoSearchDTO = converProductoSearchDTO(inventarioAddDTO.getProductoAddDTO().getReferencia(),inventarioAddDTO.getProductoAddDTO().getTalla()
                ,inventarioAddDTO.getProductoAddDTO().getColor());
        Optional<InventarioResponseDTO> inventarioOpt = findByAtributes(productoSearchDTO);
        if(inventarioOpt.isEmpty()){
            // Ingresa si el producto no esta y lo crea
            Producto producto = this.productoService.addProduct(inventarioAddDTO.getProductoAddDTO());
            Inventario inventarioCreate = new Inventario();
            inventarioCreate.setCantidad(inventarioAddDTO.getCantidad());
            inventarioCreate.setProducto(producto);
            Inventario inventario = this.inventarioRepository.save(inventarioCreate);
            // Convierte el producto en un dto InventarioResponseDTO
            return convertInventarioResponseDTO(inventario);
        }

        return inventarioOpt.get();
    }

    private List<Inventario> getInventario(List<Long> ids){
        return this.inventarioRepository.findAllByProductoIdIn(ids);
    }

    public List<InventarioResponseDTO> getProducts(List<Long> ids){
       List<Inventario> inventarios = getInventario(ids);
       return inventarios.stream()
               .map(this::convertInventarioResponseDTO)
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
