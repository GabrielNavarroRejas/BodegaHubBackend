package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.Bodega;
import com.bodegahub.sistema_bodega.repository.BodegaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodegaService {

    private final BodegaRepository bodegaRepository;

    public BodegaService(BodegaRepository bodegaRepository) {
        this.bodegaRepository = bodegaRepository;
    }


    // Método existente - mantener para compatibilidad
    public List<Bodega> listarTodas() {
        return bodegaRepository.findAll();
    }

    // Nuevo método con paginación
    public Page<Bodega> listarTodasPaginadas(Pageable pageable) {
        return bodegaRepository.findAll(pageable);
    }

    // Búsqueda paginada por nombre
    public Page<Bodega> buscarPorNombre(String nombre, Pageable pageable) {
        return bodegaRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    // Mantener métodos existentes
    public Optional<Bodega> buscarPorId(Long id) {
        return bodegaRepository.findById(id);
    }

    public Bodega guardar(Bodega bodega) {
        return bodegaRepository.save(bodega);
    }

    public void eliminar(Long id) {
        bodegaRepository.deleteById(id);
    }
}