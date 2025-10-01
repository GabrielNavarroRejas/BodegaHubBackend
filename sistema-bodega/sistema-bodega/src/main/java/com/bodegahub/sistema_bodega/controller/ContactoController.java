package com.bodegahub.sistema_bodega.controller;

import com.bodegahub.sistema_bodega.model.Contacto;
import com.bodegahub.sistema_bodega.service.ContactoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contactos")
public class ContactoController {

    private final ContactoService contactoService;
    private static final Logger logger = LoggerFactory.getLogger(ContactoController.class);

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @PostMapping
    public ResponseEntity<?> crearContacto(@RequestBody Contacto contacto) {
        try {
            Contacto contactoGuardado = contactoService.guardarContacto(contacto);
            return ResponseEntity.ok(contactoGuardado);
        } catch (Exception e) {
            logger.error("Error al guardar contacto", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la solicitud"));
        }
    }

    @GetMapping
    public List<Contacto> obtenerContactos() {
        return contactoService.listarContactos();
    }
}