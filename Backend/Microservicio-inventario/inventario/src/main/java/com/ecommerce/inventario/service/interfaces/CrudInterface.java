package com.ecommerce.inventario.service.interfaces;

import com.ecommerce.inventario.model.dto.ProductoSearchDTO;

import java.util.List;
import java.util.Optional;

public interface CrudInterface<T, Dresponse , Dsearch, Dadd> {


    Optional<Dresponse> findByAtributes(Dsearch searchElement);

    public List<Dresponse> findAll();

    public Dresponse addElement(Dadd addElement);

    // public Optional<Dresponse> updateElement(Dupdate updateElement);

}
