package com.bodegahub.sistema_bodega.repository;

import com.bodegahub.sistema_bodega.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {}

