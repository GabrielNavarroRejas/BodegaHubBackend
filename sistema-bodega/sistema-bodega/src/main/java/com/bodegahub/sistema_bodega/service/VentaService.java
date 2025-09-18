package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.*;
import com.bodegahub.sistema_bodega.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoRepository productoRepository;

    public VentaService(VentaRepository ventaRepository,
                        DetalleVentaRepository detalleVentaRepository,
                        ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Venta registrarVenta(Venta venta) {
        // Validar que la venta tenga detalles
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new RuntimeException("La venta debe tener al menos un producto");
        }

        double total = 0.0;

        // Primero guardar la venta para obtener el ID
        venta.setTotal(0.0); // Temporal hasta calcular el total real
        Venta ventaGuardada = ventaRepository.save(venta);

        // Procesar cada detalle de venta
        for (DetalleVenta detalle : venta.getDetalles()) {
            // Validar que el producto exista
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalle.getProducto().getId()));

            // Validar stock suficiente
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre() +
                        ". Stock disponible: " + producto.getStock());
            }

            // Reducir stock del producto
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            // Calcular subtotal y precio unitario
            detalle.setPrecio_unitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
            detalle.setVenta(ventaGuardada); // Establecer la relación con la venta guardada

            // Guardar el detalle
            DetalleVenta detalleGuardado = detalleVentaRepository.save(detalle);
            total += detalleGuardado.getSubtotal();
        }

        // Actualizar el total de la venta
        ventaGuardada.setTotal(total);
        return ventaRepository.save(ventaGuardada);
    }

    public List<Venta> obtenerVentas() {
        return ventaRepository.findAll();
    }

    // Nuevo método para obtener ventas por bodega
    public List<Venta> obtenerVentasPorBodega(Long idBodega) {
        return ventaRepository.findByBodegaId(idBodega);
    }

    // Nuevo método para obtener ventas por usuario
    public List<Venta> obtenerVentasPorUsuario(Long idUsuario) {
        return ventaRepository.findByUsuarioId(idUsuario);
    }

    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    @Transactional
    public void eliminarVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Restaurar stock de productos
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        ventaRepository.deleteById(id);
    }

    // Método para generar reporte de ventas por período
    public List<Venta> obtenerVentasPorPeriodo(Date fechaInicio, Date fechaFin) {
        return ventaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}



