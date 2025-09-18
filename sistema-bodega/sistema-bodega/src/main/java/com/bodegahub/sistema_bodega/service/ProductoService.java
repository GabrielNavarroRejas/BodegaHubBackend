package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.Producto;
import com.bodegahub.sistema_bodega.model.Bodega;
import com.bodegahub.sistema_bodega.repository.ProductoRepository;
import com.bodegahub.sistema_bodega.repository.BodegaRepository;
import com.bodegahub.sistema_bodega.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final BodegaRepository bodegaRepository;


    // Métodos existentes para compatibilidad
    public List<Producto> obtenerProductosPorBodega(Long idBodega) {
        Bodega bodega = bodegaRepository.findById(idBodega)
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));
        return productoRepository.findByBodega(bodega);
    }

    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }


    // Nuevos métodos con paginación
    public Page<Producto> obtenerTodosProductos(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    public Page<Producto> obtenerProductosPorBodega(Long idBodega, Pageable pageable) {
        Bodega bodega = bodegaRepository.findById(idBodega)
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));
        return productoRepository.findByBodega(bodega, pageable);
    }

    public Page<Producto> buscarPorNombre(String nombre, Pageable pageable) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    public Page<Producto> obtenerProductosConStockBajo(Integer stockLimite, Pageable pageable) {
        return productoRepository.findByStockLessThan(stockLimite, pageable);
    }

    public Page<Producto> obtenerProductosPorPrecio(Double min, Double max, Pageable pageable) {
        return productoRepository.findByPrecioBetween(min, max, pageable);
    }

    public ProductoService(ProductoRepository productoRepository, BodegaRepository bodegaRepository) {
        this.productoRepository = productoRepository;
        this.bodegaRepository = bodegaRepository;
    }

    public Optional<Producto> obtenerProducto(Long id) {
        return productoRepository.findById(id);
    }

    //@PreAuthorize("hasAnyRole('BODEGUERO', 'ADMIN')")
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    //@PreAuthorize("hasAnyRole('BODEGUERO', 'ADMIN')")
    public Producto crearProducto(Long idBodega, Producto producto) {
        Bodega bodega = bodegaRepository.findById(idBodega)
                .orElseThrow(() -> new EntityNotFoundException("Bodega", idBodega));
        producto.setBodega(bodega);
        return productoRepository.save(producto);
    }

    //@PreAuthorize("hasAnyRole('BODEGUERO', 'ADMIN')")
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        return productoRepository.findById(id).map(p -> {
            p.setNombre(productoActualizado.getNombre());
            p.setDescripcion(productoActualizado.getDescripcion());
            p.setPrecio(productoActualizado.getPrecio());
            p.setStock(productoActualizado.getStock());
            return productoRepository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException("Producto", id));
    }
}


