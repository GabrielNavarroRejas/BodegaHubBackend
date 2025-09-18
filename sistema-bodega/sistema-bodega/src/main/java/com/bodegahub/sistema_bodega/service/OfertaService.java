package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.Oferta;
import com.bodegahub.sistema_bodega.repository.OfertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    public OfertaService(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    public Oferta crearOferta(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public List<Oferta> listarOfertas() {
        return ofertaRepository.findAll();
    }
}

