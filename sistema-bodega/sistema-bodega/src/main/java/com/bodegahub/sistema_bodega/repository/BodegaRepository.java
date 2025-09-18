package com.bodegahub.sistema_bodega.repository;

import com.bodegahub.sistema_bodega.model.Bodega;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    // Paginación por defecto
    Page<Bodega> findAll(Pageable pageable);

    // Búsqueda paginada por nombre
    Page<Bodega> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}

