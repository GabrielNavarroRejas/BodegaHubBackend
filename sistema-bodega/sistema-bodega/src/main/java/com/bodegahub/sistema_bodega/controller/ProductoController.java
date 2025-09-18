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

    // Crear producto en una bodega
    @PostMapping("/bodega/{idBodega}")
    public Producto crearProducto(@PathVariable Long idBodega, @RequestBody Producto producto) {
        return productoService.crearProducto(idBodega, producto);
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
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerProducto(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
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
}


