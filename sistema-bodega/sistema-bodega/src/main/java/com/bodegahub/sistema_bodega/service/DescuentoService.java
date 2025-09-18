package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.Descuento;
import com.bodegahub.sistema_bodega.repository.DescuentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescuentoService {

    private final DescuentoRepository descuentoRepository;

    public DescuentoService(DescuentoRepository descuentoRepository) {
        this.descuentoRepository = descuentoRepository;
    }

    public Descuento crearDescuento(Descuento descuento) {
        return descuentoRepository.save(descuento);
    }

    public List<Descuento> listarDescuentos() {
        return descuentoRepository.findAll();
    }

    public List<Descuento> listarPorProducto(Long id_producto) {
        return descuentoRepository.findByProductoId(id_producto);
    }
}

