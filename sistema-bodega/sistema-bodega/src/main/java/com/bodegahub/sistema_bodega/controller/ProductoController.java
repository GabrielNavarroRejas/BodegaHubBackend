package com.bodegahub.sistema_bodega.controller;

import com.bodegahub.sistema_bodega.model.Producto;
import com.bodegahub.sistema_bodega.service.ProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Crear producto en una bodega - AHORA CON CÓDIGO DE BARRAS AUTOMÁTICO
    @PostMapping("/bodega/{idBodega}")
    public ResponseEntity<Producto> crearProducto(@PathVariable Long idBodega, @RequestBody Producto producto) {
        // El código de barras se genera automáticamente en el modelo (@PrePersist)
        // No es necesario enviarlo en el JSON
        Producto nuevoProducto = productoService.crearProducto(idBodega, producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @GetMapping
    public List<Producto> listarTodos() {
        return productoService.obtenerTodosProductos();
    }

    // Obtener productos de una bodega
    @GetMapping("/bodega/{idBodega}")
    public List<Producto> obtenerProductosPorBodega(@PathVariable Long idBodega) {
        return productoService.obtenerProductosPorBodega(idBodega);
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerProducto(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ResponseEntity.ok(producto);
    }

    // Obtener producto por código de barras
    @GetMapping("/codigo-barras/{codigoBarras}")
    public ResponseEntity<Producto> obtenerProductoPorCodigoBarras(@PathVariable String codigoBarras) {
        Producto producto = productoService.obtenerProductoPorCodigoBarras(codigoBarras)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con código: " + codigoBarras));
        return ResponseEntity.ok(producto);
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    // Nuevos endpoints con paginación
    @GetMapping("/paginated")
    public ResponseEntity<Page<Producto>> listarTodosPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Producto> productos = productoService.obtenerTodosProductos(pageable);

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/bodega/{idBodega}/paginated")
    public ResponseEntity<Page<Producto>> obtenerProductosPorBodegaPaginated(
            @PathVariable Long idBodega,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Producto> productos = productoService.obtenerProductosPorBodega(idBodega, pageable);

        return ResponseEntity.ok(productos);
    }

    // Buscar productos por nombre (con paginación)
    @GetMapping("/buscar")
    public ResponseEntity<Page<Producto>> buscarProductos(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Producto> productos = productoService.buscarProductosPorNombre(nombre, pageable);

        return ResponseEntity.ok(productos);
    }
}