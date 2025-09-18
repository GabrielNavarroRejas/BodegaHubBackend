package com.bodegahub.sistema_bodega.repository;

import com.bodegahub.sistema_bodega.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}

