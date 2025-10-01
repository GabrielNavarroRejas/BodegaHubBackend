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

    public ProductoService(ProductoRepository productoRepository, BodegaRepository bodegaRepository) {
        this.productoRepository = productoRepository;
        this.bodegaRepository = bodegaRepository;
    }

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

    // NUEVO: Buscar productos por nombre (para el endpoint de búsqueda)
    public Page<Producto> buscarProductosPorNombre(String nombre, Pageable pageable) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    // NUEVO: Obtener producto por código de barras
    public Optional<Producto> obtenerProductoPorCodigoBarras(String codigoBarras) {
        return productoRepository.findByCodigoBarras(codigoBarras);
    }

    public Page<Producto> obtenerProductosConStockBajo(Integer stockLimite, Pageable pageable) {
        return productoRepository.findByStockLessThan(stockLimite, pageable);
    }

    public Page<Producto> obtenerProductosPorPrecio(Double min, Double max, Pageable pageable) {
        return productoRepository.findByPrecioBetween(min, max, pageable);
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

        // El código de barras se genera automáticamente en el modelo (@PrePersist)
        // No necesitamos generarlo manualmente aquí
        return productoRepository.save(producto);
    }

    //@PreAuthorize("hasAnyRole('BODEGUERO', 'ADMIN')")
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        return productoRepository.findById(id).map(productoExistente -> {
            // Actualizar campos permitidos
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setDescripcion(productoActualizado.getDescripcion());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            productoExistente.setStock(productoActualizado.getStock());

            // NUEVO: Actualizar URL de imagen si se proporciona
            if (productoActualizado.getUrlImagen() != null) {
                productoExistente.setUrlImagen(productoActualizado.getUrlImagen());
            }

            // NO actualizamos el código de barras - es inmutable una vez generado
            // NO actualizamos la bodega - usar método específico si es necesario

            return productoRepository.save(productoExistente);
        }).orElseThrow(() -> new EntityNotFoundException("Producto", id));
    }

    // MÉTODO ADICIONAL: Actualizar solo el stock de un producto
    //@PreAuthorize("hasAnyRole('BODEGUERO', 'ADMIN')")
    public Producto actualizarStock(Long id, Integer nuevoStock) {
        return productoRepository.findById(id).map(producto -> {
            producto.setStock(nuevoStock);
            return productoRepository.save(producto);
        }).orElseThrow(() -> new EntityNotFoundException("Producto", id));
    }
}