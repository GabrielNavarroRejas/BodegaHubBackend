package com.bodegahub.sistema_bodega.repository;

import com.bodegahub.sistema_bodega.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
    List<Valoracion> findByProductoId(Long id_producto);
}

