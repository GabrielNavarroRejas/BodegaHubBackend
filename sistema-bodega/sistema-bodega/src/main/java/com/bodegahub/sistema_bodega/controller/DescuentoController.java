package com.bodegahub.sistema_bodega.controller;

import com.bodegahub.sistema_bodega.model.Descuento;
import com.bodegahub.sistema_bodega.service.DescuentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/descuentos")
public class DescuentoController {

    private final DescuentoService descuentoService;

    public DescuentoController(DescuentoService descuentoService) {
        this.descuentoService = descuentoService;
    }

    @PostMapping
    public Descuento crearDescuento(@RequestBody Descuento descuento) {
        return descuentoService.crearDescuento(descuento);
    }

    @GetMapping
    public List<Descuento> listarDescuentos() {
        return descuentoService.listarDescuentos();
    }

    @GetMapping("/producto/{id_producto}")
    public List<Descuento> listarPorProducto(@PathVariable Long id_producto) {
        return descuentoService.listarPorProducto(id_producto);
    }
}

