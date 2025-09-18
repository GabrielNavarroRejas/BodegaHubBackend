package com.bodegahub.sistema_bodega.controller;

import com.bodegahub.sistema_bodega.model.Valoracion;
import com.bodegahub.sistema_bodega.service.ValoracionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    private final ValoracionService valoracionService;

    public ValoracionController(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    @PostMapping
    public Valoracion crearValoracion(@RequestBody Valoracion valoracion) {
        return valoracionService.crearValoracion(valoracion);
    }

    @GetMapping
    public List<Valoracion> listarValoraciones() {
        return valoracionService.listarValoraciones();
    }

    @GetMapping("/producto/{id_producto}")
    public List<Valoracion> listarPorProducto(@PathVariable Long id_producto) {
        return valoracionService.listarPorProducto(id_producto);
    }
}

