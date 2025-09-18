package com.bodegahub.sistema_bodega.controller;

import com.bodegahub.sistema_bodega.model.Bodega;
import com.bodegahub.sistema_bodega.service.BodegaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BodegaController.class)
class BodegaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BodegaService bodegaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarBodegas() throws Exception {
        // Arrange
        Bodega bodega1 = new Bodega();
        bodega1.setIdBodega(1L);
        bodega1.setNombre("Bodega Norte");

        Bodega bodega2 = new Bodega();
        bodega2.setIdBodega(2L);
        bodega2.setNombre("Bodega Sur");

        List<Bodega> bodegas = Arrays.asList(bodega1, bodega2);
        when(bodegaService.listarTodas()).thenReturn(bodegas);

        // Act & Assert
        mockMvc.perform(get("/api/bodegas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Bodega Norte"))
                .andExpect(jsonPath("$[1].nombre").value("Bodega Sur"));

        verify(bodegaService, times(1)).listarTodas();
    }

    @Test
    void testObtenerBodegaPorId_Existente() throws Exception {
        // Arrange
        Bodega bodega = new Bodega();
        bodega.setIdBodega(1L);
        bodega.setNombre("Bodega Test");

        when(bodegaService.buscarPorId(1L)).thenReturn(Optional.of(bodega));

        // Act & Assert
        mockMvc.perform(get("/api/bodegas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBodega").value(1))
                .andExpect(jsonPath("$.nombre").value("Bodega Test"));

        verify(bodegaService, times(1)).buscarPorId(1L);
    }

    @Test
    void testObtenerBodegaPorId_NoExistente() throws Exception {
        // Arrange
        when(bodegaService.buscarPorId(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/bodegas/999"))
                .andExpect(status().isNotFound());

        verify(bodegaService, times(1)).buscarPorId(999L);
    }

    @Test
    void testCrearBodega_Valida() throws Exception {
        // Arrange
        Bodega bodega = new Bodega();
        bodega.setNombre("Nueva Bodega");
        bodega.setDireccion("Calle 123");

        Bodega bodegaGuardada = new Bodega();
        bodegaGuardada.setIdBodega(1L);
        bodegaGuardada.setNombre("Nueva Bodega");
        bodegaGuardada.setDireccion("Calle 123");

        when(bodegaService.guardar(any(Bodega.class))).thenReturn(bodegaGuardada);

        // Act & Assert
        mockMvc.perform(post("/api/bodegas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bodega)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBodega").value(1))
                .andExpect(jsonPath("$.nombre").value("Nueva Bodega"));

        verify(bodegaService, times(1)).guardar(any(Bodega.class));
    }

    @Test
    void testCrearBodega_Invalida() throws Exception {
        // Arrange
        Bodega bodegaInvalida = new Bodega();
        bodegaInvalida.setNombre(""); // Nombre vacío - inválido

        // Act & Assert
        mockMvc.perform(post("/api/bodegas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bodegaInvalida)))
                .andExpect(status().isBadRequest());
    }
}
