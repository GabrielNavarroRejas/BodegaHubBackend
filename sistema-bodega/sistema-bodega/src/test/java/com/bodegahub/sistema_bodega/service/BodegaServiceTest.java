package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.Bodega;
import com.bodegahub.sistema_bodega.repository.BodegaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BodegaServiceTest {

    @Mock
    private BodegaRepository bodegaRepository;

    @InjectMocks
    private BodegaService bodegaService;

    @Test
    void testListarTodas() {
        // Arrange
        Bodega bodega1 = new Bodega();
        bodega1.setIdBodega(1L);
        bodega1.setNombre("Bodega Norte");

        Bodega bodega2 = new Bodega();
        bodega2.setIdBodega(2L);
        bodega2.setNombre("Bodega Sur");

        List<Bodega> bodegas = Arrays.asList(bodega1, bodega2);
        when(bodegaRepository.findAll()).thenReturn(bodegas);

        // Act
        List<Bodega> resultado = bodegaService.listarTodas();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Bodega Norte", resultado.get(0).getNombre());
        verify(bodegaRepository, times(1)).findAll();
    }

    @Test
    void testListarTodasPaginadas() {
        // Arrange
        Bodega bodega1 = new Bodega();
        bodega1.setIdBodega(1L);
        bodega1.setNombre("Bodega Norte");

        Bodega bodega2 = new Bodega();
        bodega2.setIdBodega(2L);
        bodega2.setNombre("Bodega Sur");

        List<Bodega> bodegas = Arrays.asList(bodega1, bodega2);
        Page<Bodega> page = new PageImpl<>(bodegas);
        Pageable pageable = PageRequest.of(0, 10);

        when(bodegaRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<Bodega> resultado = bodegaService.listarTodasPaginadas(pageable);

        // Assert
        assertEquals(2, resultado.getTotalElements());
        assertEquals("Bodega Norte", resultado.getContent().get(0).getNombre());
        verify(bodegaRepository, times(1)).findAll(pageable);
    }

    @Test
    void testBuscarPorId_Existente() {
        // Arrange
        Bodega bodega = new Bodega();
        bodega.setIdBodega(1L);
        bodega.setNombre("Bodega Test");

        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));

        // Act
        Optional<Bodega> resultado = bodegaService.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Bodega Test", resultado.get().getNombre());
        verify(bodegaRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_NoExistente() {
        // Arrange
        when(bodegaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Bodega> resultado = bodegaService.buscarPorId(999L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(bodegaRepository, times(1)).findById(999L);
    }

    @Test
    void testGuardarBodega() {
        // Arrange
        Bodega bodega = new Bodega();
        bodega.setNombre("Nueva Bodega");

        when(bodegaRepository.save(any(Bodega.class))).thenReturn(bodega);

        // Act
        Bodega resultado = bodegaService.guardar(bodega);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nueva Bodega", resultado.getNombre());
        verify(bodegaRepository, times(1)).save(bodega);
    }

    @Test
    void testEliminarBodega() {
        // Act
        bodegaService.eliminar(1L);

        // Assert
        verify(bodegaRepository, times(1)).deleteById(1L);
    }
}
