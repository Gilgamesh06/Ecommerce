package com.ecommerce.venta.service.impl;

import com.ecommerce.venta.model.entity.Precio;
import com.ecommerce.venta.model.dto.precio.PrecioAddDTO;
import com.ecommerce.venta.model.dto.precio.PrecioResponseDTO;
import com.ecommerce.venta.model.entity.Producto;
import com.ecommerce.venta.repository.PrecioRepository;
import com.ecommerce.venta.service.interfaces.GetAndSave;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrecioService implements GetAndSave<Precio, PrecioResponseDTO, PrecioAddDTO> {

    private final PrecioRepository precioRepository;

    public PrecioService(PrecioRepository precioRepository){
        this.precioRepository = precioRepository;
    }

    public Optional<Precio> getPrecio(Long productoId){
       return this.precioRepository.findLatestPrecioByProductoId(productoId);
    }

    public PrecioAddDTO convertPrecioAddDTO( Double precioUnid, Double precioVenta, Producto producto){
        PrecioAddDTO precioAddDTO = new PrecioAddDTO();
        precioAddDTO.setPrecioUnid(precioUnid);
        precioAddDTO.setPrecioVenta(precioVenta);
        precioAddDTO.setProducto(producto);
        return precioAddDTO;
    }

    @Override
    public List<PrecioResponseDTO> findAll() {
        List<Precio> precios = this.precioRepository.findAll();
        return precios.stream()
                .map(this::converPrecioResponseDTO)
                .toList();
    }

    public PrecioResponseDTO converPrecioResponseDTO(Precio precio){
        PrecioResponseDTO precioResponseDTO = new PrecioResponseDTO();
        precioResponseDTO.setPrecioVenta(precio.getPrecioVenta());
        precioResponseDTO.setFecha(precio.getFecha());
        return precioResponseDTO;
    }

    public Precio addPrecio(PrecioAddDTO addPrecio) {
        Optional<Precio> precioOpt = getPrecio(addPrecio.getProducto().getId());

        // Si no existe un precio, creamos uno nuevo
        if (precioOpt.isEmpty()) {
            return crearNuevoPrecio(addPrecio);
        }

        Precio precioExistente = precioOpt.get();

        // Si los precios son diferentes, actualizamos
        if ( !precioExistente.getPrecioUnid().equals(addPrecio.getPrecioUnid()) || !precioExistente.getPrecioVenta().equals(addPrecio.getPrecioVenta())) {
            return crearNuevoPrecio(addPrecio);
        }

        // Si no hay cambios, devolvemos el precio existente
        return precioExistente;
    }

    @Transactional
    private Precio crearNuevoPrecio(PrecioAddDTO addPrecio) {
        Precio nuevoPrecio = new Precio();
        nuevoPrecio.setPrecioUnid(addPrecio.getPrecioUnid());
        nuevoPrecio.setPrecioVenta(addPrecio.getPrecioVenta());
        nuevoPrecio.setFecha(LocalDateTime.now());
        nuevoPrecio.setProducto(addPrecio.getProducto());
        return this.precioRepository.save(nuevoPrecio);
    }

    @Override
    public PrecioResponseDTO addElement(PrecioAddDTO addPrecio) {
        return converPrecioResponseDTO(addPrecio(addPrecio));
    }
}
