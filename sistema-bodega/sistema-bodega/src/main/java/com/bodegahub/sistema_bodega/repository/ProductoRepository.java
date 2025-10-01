package com.bodegahub.sistema_bodega.repository;

import com.bodegahub.sistema_bodega.model.Producto;
import com.bodegahub.sistema_bodega.model.Bodega;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Paginación de todos los productos
    Page<Producto> findAll(Pageable pageable);

    // Productos por bodega con paginación
    Page<Producto> findByBodega(Bodega bodega, Pageable pageable);

    // Búsqueda paginada por nombre
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Productos con stock bajo con paginación
    Page<Producto> findByStockLessThan(Integer stock, Pageable pageable);

    // Productos por rango de precio con paginación
    Page<Producto> findByPrecioBetween(Double precioMin, Double precioMax, Pageable pageable);

    // NUEVO: Buscar producto por código de barras
    Optional<Producto> findByCodigoBarras(String codigoBarras);

    // Mantener métodos existentes para compatibilidad
    List<Producto> findByBodega(Bodega bodega);
}