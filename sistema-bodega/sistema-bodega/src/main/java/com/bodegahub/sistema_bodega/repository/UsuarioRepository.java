package com.bodegahub.sistema_bodega.repository;

import com.bodegahub.sistema_bodega.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    // Paginación de usuarios
    Page<Usuario> findAll(Pageable pageable);

    // Usuarios por rol con paginación
    Page<Usuario> findByRol(String rol, Pageable pageable);

    // Búsqueda paginada por nombre
    Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}

