package com.bodegahub.sistema_bodega.repository;

import com.bodegahub.sistema_bodega.model.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DescuentoRepository extends JpaRepository<Descuento, Long> {
    List<Descuento> findByProductoId(Long id_producto);
}

