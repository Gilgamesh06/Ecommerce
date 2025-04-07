package com.ecommerce.venta.service.interfaces;

import java.util.List;
import java.util.Optional;

public interface GetAndSave<T, Dresponse, Dadd> {

    public List<Dresponse> findAll();

    public Dresponse addElement(Dadd addElement);
}
