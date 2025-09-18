package com.bodegahub.sistema_bodega.controller;

import com.bodegahub.sistema_bodega.model.Venta;
import com.bodegahub.sistema_bodega.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // Registrar una venta
    @PostMapping
    public ResponseEntity<Venta> registrarVenta(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.registrarVenta(venta));
    }

    // Obtener todas las ventas
    @GetMapping
    public ResponseEntity<List<Venta>> obtenerVentas() {
        return ResponseEntity.ok(ventaService.obtenerVentas());
    }

    // Obtener ventas por bodega
    @GetMapping("/bodega/{idBodega}")
    public ResponseEntity<List<Venta>> obtenerVentasPorBodega(@PathVariable Long idBodega) {
        return ResponseEntity.ok(ventaService.obtenerVentasPorBodega(idBodega));
    }

    // Obtener ventas por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Venta>> obtenerVentasPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ventaService.obtenerVentasPorUsuario(idUsuario));
    }

    // Obtener venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


