package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.Valoracion;
import com.bodegahub.sistema_bodega.repository.ValoracionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;

    public ValoracionService(ValoracionRepository valoracionRepository) {
        this.valoracionRepository = valoracionRepository;
    }

    public Valoracion crearValoracion(Valoracion valoracion) {
        return valoracionRepository.save(valoracion);
    }

    public List<Valoracion> listarValoraciones() {
        return valoracionRepository.findAll();
    }

    public List<Valoracion> listarPorProducto(Long id_producto) {
        return valoracionRepository.findByProductoId(id_producto);
    }
}

