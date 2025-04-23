package com.ecommerce.inventario.service.impl;

import com.ecommerce.inventario.model.dto.catalogo.ProductoAgrupadoDTO;
import com.ecommerce.inventario.model.dto.catalogo.VarianteDTO;
import com.ecommerce.inventario.model.dto.detalleproducto.DetalleProductoResponseDTO;
import com.ecommerce.inventario.model.entity.Producto;
import com.ecommerce.inventario.repository.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CatalogoService {

    private final ProductoRepository productoRepository;
    private final DetalleProductoService detalleProductoService;

    public CatalogoService(ProductoRepository productoRepository, DetalleProductoService detalleProductoService){
        this.productoRepository = productoRepository;
        this.detalleProductoService = detalleProductoService;
    }

    private List<String> getTargets(String target) {
        return new ArrayList<>(Arrays.asList("Unisex", target));
    }

    private Page<Producto> getProductsByTarget(String target, Pageable pageable){
        List<String> targets = getTargets(target);
        return productoRepository.findByTargetIn(targets, pageable);
    }

    private Page<Producto> getProductsByTargetAndTipo(String target, String tipo, Pageable pageable){
        List<String> targets = getTargets(target);
        return productoRepository.findByTargetInAndTipo(targets, tipo, pageable);
    }

    private Page<Producto> getProductsByTargetAndTipoAndSubtipo(String target, String tipo, String subtipo, Pageable pageable){
        List<String> targets = getTargets(target);
        return productoRepository.findByTargetInAndTipoAndSubtipo(targets, tipo, subtipo, pageable);
    }


    private Map<String, List<Producto>> groupMapProductosByReference(Page<Producto> productos){
        Map<String, List<Producto>> map = new HashMap<>();;
        if(!productos.isEmpty()){
            return productos.getContent()
                    .stream()
                    .collect(Collectors.groupingBy(Producto::getReferencia));
        }
        return map;
    }

    private Page<ProductoAgrupadoDTO> filterProducts(Page<Producto> productosPage) {
        Map<String, List<Producto>> productosPorReferencia = groupMapProductosByReference(productosPage);

        List<ProductoAgrupadoDTO> productosAgrupadosDTO = productosPorReferencia.entrySet().stream().map(entry -> {
            String referencia = entry.getKey();
            List<Producto> productosDeReferencia = entry.getValue();
            Producto productoPrincipal = productosDeReferencia.get(0);

            Set<String> tallas = new HashSet<>();
            Set<String> colores = new HashSet<>();
            List<VarianteDTO> variantes = new ArrayList<>();

            for (Producto producto : productosDeReferencia) {
                tallas.add(producto.getTalla());
                colores.add(producto.getColor());
                DetalleProductoResponseDTO detalle = this.detalleProductoService.converDetallePorductoResponse(producto.getDetalleProducto());
                variantes.add(new VarianteDTO(producto.getId(), producto.getTalla(), producto.getColor(),producto.getPrecioVenta(), detalle));
            }

            return new ProductoAgrupadoDTO(
                    referencia,
                    productoPrincipal.getNombre(),
                    tallas,
                    colores,
                    variantes
            );
        }).toList();

        return new PageImpl<>(productosAgrupadosDTO, productosPage.getPageable(), productosPage.getTotalElements());
    }

    public Page<ProductoAgrupadoDTO> filterProductsTargets(String target, String tipo, String subtipo, Pageable pageable){
        Page<Producto> productosPage;

        if (tipo == null || tipo.isBlank()) {
            productosPage = getProductsByTarget(target, pageable);
        } else if (subtipo == null || subtipo.isBlank()) {
            productosPage = getProductsByTargetAndTipo(target, tipo, pageable);
        } else {
            productosPage = getProductsByTargetAndTipoAndSubtipo(target, tipo, subtipo, pageable);
        }

        return filterProducts(productosPage);
    }

    public Map<String, Set<String>> TypesOfTheFilter(){
        Set<String> tipos = this.productoRepository.findDistinctTipos();
        Map<String, Set<String>> fitros = new HashMap<>();
        for(String tipo: tipos){
            Set<String> subtipos = this.productoRepository.findDistinctSubtiposByTipo(tipo);
            fitros.put(tipo,subtipos);
        }
        return fitros;
    }
}
