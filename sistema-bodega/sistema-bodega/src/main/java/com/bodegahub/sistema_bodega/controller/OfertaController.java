package com.bodegahub.sistema_bodega.controller;

import com.bodegahub.sistema_bodega.model.Oferta;
import com.bodegahub.sistema_bodega.service.OfertaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @PostMapping
    public Oferta crearOferta(@RequestBody Oferta oferta) {
        return ofertaService.crearOferta(oferta);
    }

    @GetMapping
    public List<Oferta> listarOfertas() {
        return ofertaService.listarOfertas();
    }
}
